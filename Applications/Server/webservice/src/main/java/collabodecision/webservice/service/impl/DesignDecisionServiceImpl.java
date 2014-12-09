package collabodecision.webservice.service.impl;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.data.RequestWrapperDesignDecision;
import collabodecision.webservice.data.ResponseWrapperDesignDecision;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.DesignDecisionDao;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.domain.DesignDecision.DesignDecisionStatus;
import collabodecision.webservice.persistence.domain.File;
import collabodecision.webservice.persistence.domain.Issue.IssueStatus;
import collabodecision.webservice.persistence.domain.ShareHolder;
import collabodecision.webservice.service.AppUserService;
import collabodecision.webservice.service.DesignDecisionService;
import collabodecision.webservice.service.IssueService;
import collabodecision.webservice.service.utils.CommentHelper;

@Service
public class DesignDecisionServiceImpl implements DesignDecisionService {

	@Autowired
	private IssueService issueService;
	
	@Autowired
	private DesignDecisionDao designDecisionDao;
	
	@Autowired
	private CommentHelper commentHelper;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private AppUserService userService;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<DesignDecision> getDesignDecisions(String status,
			List<String> tags, String partialName) {
		// TODO Auto-generated method stub
		DesignDecisionStatus designdecisionstatus = null;
		IssueStatus issueStatus = null;

		//TODO: function
		return null;
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<DesignDecision> getDesignDecisions() {
		
		return designDecisionDao.getDesignDecisions();
	}

	@Override
	@Transactional(readOnly = true)
	public DesignDecision getDesignDecision(long id, boolean withRelations) {
		if (withRelations) {
			return designDecisionDao.getDesignDecisionWithRelations(id);
		}

		return designDecisionDao.getDesignDecision(id);
	}

	@Override
	public ResponseWrapperDesignDecision getResponseWrapperDesignDesicion(
			long id, boolean withRelations) {
		DesignDecision decision;

		AppUser creator = userService
				.getAppUserByUsername(SecurityContextHolder.getContext()
						.getAuthentication().getName());

		if (withRelations) {
			decision = designDecisionDao.getDesignDecisionWithRelations(id);
		} else {
			decision = designDecisionDao.getDesignDecision(id);
		}

		ResponseWrapperDesignDecision response = new ResponseWrapperDesignDecision();
		response.setDesignDecision(decision);

		//if user == owner
		if (creator.equals(decision.getIssue().getOwner())) {
			response.setOwner(true);
			//TODO if status == collecting alternatives show "start ranking"
			if (DesignDecisionStatus.COLLECTING_ALTERNATIVES.equals(decision.getDesignDecisionStatus())) {
				response.setShowStartRanking(true);
			} 	
			else if (DesignDecisionStatus.SELECTING_ALTERNATIVES.equals(decision.getDesignDecisionStatus())) {
				//after all shareholders have finished ranking
				response.setShowSelectAlternative(true);
			}

		}
		
		//if user == shareholder
		if (decision.getShareHolders().contains(creator)) {
			response.setEditable(true);
			response.setIsShareholder(true);
			if (DesignDecisionStatus.RANK_ALTERNATIVES.equals(decision.getDesignDecisionStatus())) {
				response.setShowFinishRanking(true);
			}
		}
		
		if (DesignDecisionStatus.DECIDED.equals(decision.getDesignDecisionStatus())) {
			response.setShowDecided(true);
 		} else if(DesignDecisionStatus.INAPPROPRIATE_SOLUTION.equals(decision.getDesignDecisionStatus())) {
 			response.setShowInappropriateSolution(true);
 		} else if (DesignDecisionStatus.OBSOLETE.equals(decision.getDesignDecisionStatus())) {
 			response.setShowObsolete(true);
 		}
		return response;
	}

	@Override
	@Transactional(readOnly = false)
	public void addDesignDecision(DesignDecision designDecision) {
		// designDecisionDao.saveOrUpdateDesignDecision(designDecision);

	}

	@Override
	@Transactional(readOnly = false)
	public void updateDesignDecision(long id, DesignDecision designDecision) {
		// designDecisionDao.saveOrUpdateDesignDecision(designDecision);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteDesignDecision(long id) {
		designDecisionDao.deleteDesignDecision(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void addComment(long id, String message, String date) {
		Comment comment = commentHelper.getComment(message, date);
		comment.setDesignDecision(designDecisionDao.getDesignDecision(id));
		commentDao.saveOrUpdateComment(comment);
	}

	@Override
	public void addFile(long id, String pathToFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addRequestWrapperDesignDecision(
			RequestWrapperDesignDecision DesignDecisionrequest) {
		// TODO Auto-generated method stub
		addOrUpdateDesignDecision(DesignDecisionrequest, null);
	}

	private void addOrUpdateDesignDecision(RequestWrapperDesignDecision decisionRequest, Long idExistingDesignDecision) {
		// get DesignDecision from DB if update; otherwise new Design Decion

		DesignDecision decision = null;

		if (idExistingDesignDecision == null) {
			decision = new DesignDecision();
			decision.setCreationDate(new Date(System.currentTimeMillis()));
			decision.setIssue(issueService.getResponseWrapperIssue(decisionRequest.getIdIssue(), false).getIssue());
			decision.getIssue().setIssueStatus(IssueStatus.IN_PROGRESS);
		} else {
			designDecisionDao.getDesignDecision(idExistingDesignDecision);
		}
		// On Update - Delete all OneToMany Relations in advance (are added
		// again later)
		if (idExistingDesignDecision != null) {
			decision.getFiles().clear();
			// Must be done - Otherwise Hibernate would result in Violation
			// Constraint!
			decision.getShareHolders().clear();
			
			sessionFactory.getCurrentSession().flush();
		}

		// Setting the properties of the DesignDecision
		decision.setTitle(decisionRequest.getTitle());
		decision.setAssumption(decisionRequest.getAssumption());

		decision.setIssue(issueService.getResponseWrapperIssue(decisionRequest.getIdIssue(), false).getIssue());
		
		//set shareholders
		Set<ShareHolder> shareholders = new HashSet<ShareHolder>();
		for (Long appUserId : decisionRequest.getAppUserIds()) {
			AppUser appUser = userService.getAppUser(appUserId);
			shareholders.add(new ShareHolder(appUser, decision));		
		}
		decision.setShareHolders(shareholders);

		
//		//TODO: make column rationale nullable and remove
//		decision.setRationale("");
		decision.setDesignDecisionStatus(DesignDecisionStatus.valueOf(decisionRequest.getDesignDecisionStatus()));
		
		if (decisionRequest.getFiles() != null) {
			for (String file : decisionRequest.getFiles()) {
				decision.getFiles().add(new File(file, decision));
			}

		}

		// Only needed when new (not update)
		if (idExistingDesignDecision == null) {
			designDecisionDao.saveOrUpdateDesignDecision(decision);
		}

	}

}
