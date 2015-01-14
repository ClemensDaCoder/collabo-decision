package collabodecision.webservice.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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
import collabodecision.webservice.persistence.AlternativeRankingDao;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.DesignDecisionDao;
import collabodecision.webservice.persistence.DesignDecisionRatingDao;
import collabodecision.webservice.persistence.ShareDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.AlternativeRanking;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.domain.Issue;
import collabodecision.webservice.persistence.domain.DesignDecision.DesignDecisionStatus;
import collabodecision.webservice.persistence.domain.DesignDecisionRating;
import collabodecision.webservice.persistence.domain.File;
import collabodecision.webservice.persistence.domain.Share;
import collabodecision.webservice.service.AlternativeService;
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
	AlternativeService alternativeService;

	@Autowired
	private CommentHelper commentHelper;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private ShareDao shareDao;

	@Autowired
	private AppUserService userService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DesignDecisionRatingDao designDecisionRatingDao;
	
	@Autowired
	private AlternativeRankingDao alternativeRankingDao;

	@Override
	public List<ResponseWrapperDesignDecision> getDesignDecisions(
			String status, boolean isShareholder, boolean toRank, boolean toRate) {
		DesignDecisionStatus decisionstatus = status != null ? DesignDecisionStatus
				.valueOf(status) : null;

		List<ResponseWrapperDesignDecision> responses = new ArrayList<>();
		for (DesignDecision decision : designDecisionDao.getDesignDecisions(
				decisionstatus, isShareholder, toRank, toRate)) {
			responses.add(wrapDesignDecision(decision));
		}
		return responses;

	}

	@Override
	@Transactional(readOnly = true)
	public List<ResponseWrapperDesignDecision> getDesignDecisions() {
		List<ResponseWrapperDesignDecision> responses = new ArrayList<>();
		for (DesignDecision decision : designDecisionDao.getDesignDecisions()) {
			responses.add(wrapDesignDecision(decision));
		}
		return responses;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseWrapperDesignDecision getDesignDecision(long id, boolean withRelations) {
		if (withRelations) {
			calculateRanking(designDecisionDao.getDesignDecisionWithRelations(id));
			return wrapDesignDecision(designDecisionDao.getDesignDecisionWithRelations(id));
		}
		return wrapDesignDecision(designDecisionDao.getDesignDecision(id));
	}

	@Override
	@Transactional(readOnly = false)
	public void updateDesignDecision(long id,
			RequestWrapperDesignDecision designDecisionRequest) {

		// Means that only a status change should be executed
		if (designDecisionRequest.isOnlyStatusChange()) {

			DesignDecision designDecision = designDecisionDao
					.getDesignDecision(id);
			designDecision
					.setDesignDecisionStatus(DesignDecisionStatus.valueOf(designDecisionRequest.getDesignDecisionStatus()));

		} else {
			addOrUpdateDesignDecision(designDecisionRequest, id);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteDesignDecision(long id) {
		designDecisionDao.deleteDesignDecision(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void addComment(long id, String message, String date) {
		Comment comment = commentHelper.createComment(message, date);
		DesignDecision designDecision = designDecisionDao
				.getDesignDecisionWithRelations(id);
		comment.setDesignDecision(designDecision);
		designDecision.getComments().add(comment);
		commentDao.saveOrUpdateComment(comment);
		designDecisionDao.saveOrUpdateDesignDecision(designDecision);
	}

	@Override
	public void addFile(long id, String pathToFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDesignDecision(
			RequestWrapperDesignDecision DesignDecisionrequest) {
		// TODO Auto-generated method stub
		addOrUpdateDesignDecision(DesignDecisionrequest, null);
	}

	@Override
	public List<Comment> getChildComments(long idComment) {
		return commentDao.getChildComments(idComment);
	}

	private void addOrUpdateDesignDecision(
			RequestWrapperDesignDecision decisionRequest,
			Long idExistingDesignDecision) {

		// get DesignDecision from DB if update; otherwise new Design Decion
		DesignDecision decision = idExistingDesignDecision != null ? designDecisionDao
				.getDesignDecision(idExistingDesignDecision)
				: new DesignDecision();

		if (idExistingDesignDecision != null) {
			decision.getShares().clear();
			// decision.getAlternatives().clear();
			// decision.getComments().clear();
			decision.getFiles().clear();

			// Must be done - Otherwise Hibernate would result in Violation
			// Constraint!
			sessionFactory.getCurrentSession().flush();
		} else {
			decision.setCreationDate(new Date(System.currentTimeMillis()));
		}

		// Setting the properties of the DesignDecision
		decision.setTitle(decisionRequest.getTitle());
		decision.setAssumption(decisionRequest.getAssumption());

		// set shareholders
		Set<Share> shareholders = new HashSet<Share>();
		for (Long appUserId : decisionRequest.getAppUserIds()) {
			AppUser appUser = userService.getAppUser(appUserId);

			// Only check for existing shares when Update
			if (idExistingDesignDecision != null) {
				Share share = shareDao.getShare(appUser, decision);
				shareholders.add(share != null ? share : new Share(appUser,
						decision));
			} else {
				shareholders.add(new Share(appUser, decision));
			}
		}

		decision.setShares(shareholders);

		decision.setRationale(decisionRequest.getRationale());

		
		DesignDecisionStatus status = DesignDecisionStatus.valueOf(decisionRequest.getDesignDecisionStatus());
		
		decision.setDesignDecisionStatus(status);

		Issue issue = issueService
				.getIssue(decisionRequest.getIdIssue(), false).getIssue();

		decision.setIssue(issue);

		setIssueStatus(issue, status);

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

	/**
	 * Help method for deciding the status of an issue according to changes in
	 * the DesignDecision
	 * 
	 * @param issue
	 *            The Issue
	 * @param decision
	 *            The new DesignDecision to the Issue
	 */
	private void setIssueStatus(Issue issue, DesignDecisionStatus decisionStatus) {
		
		switch(decisionStatus) {
		case BLOCKED:
			break;
		case COLLECTING_ALTERNATIVES:
			issue.setIssueStatus(Issue.IssueStatus.IN_PROGRESS);
			break;
		case DECIDED:
			issue.setIssueStatus(Issue.IssueStatus.RESOLVED);
			break;
		case INAPPROPRIATE_SOLUTION:
			issue.setIssueStatus(Issue.IssueStatus.IN_PROGRESS);
			break;
		case OBSOLETE:
			issue.setIssueStatus(Issue.IssueStatus.OBSOLETE);
			break;
		case RANK_ALTERNATIVES:
			issue.setIssueStatus(Issue.IssueStatus.IN_PROGRESS);
			break;
		case SELECTING_ALTERNATIVES:
			issue.setIssueStatus(Issue.IssueStatus.IN_PROGRESS);
			break;
		default:
			break;
		
		
		}
		
		
	}

	private DesignDecision calculateRanking(DesignDecision decision) {
		int[] values = new int[decision.getAlternatives().size()];
		int i = 0;
		for (Alternative a : decision.getAlternatives()) {

			int sum = 0;
			for (AlternativeRanking ar : a.getAlternativeRankings()) {
				sum += ar.getRank();
			}
			a.setRankingpoints(sum);
			values[i] = sum;
			i++;
		}
		Arrays.sort(values);
		for (Alternative a : decision.getAlternatives()) {
			for (int j = 0; j < values.length; j++) {
				if (a.getRankingpoints() == values[j]) {
					a.setRanking(j + 1);
				}
			}
		}
		return decision;
	}

	private ResponseWrapperDesignDecision wrapDesignDecision(
			DesignDecision decision) {

		// set Alternative Data
		// decision= calculateRanking(decision);

		// The user that made the request!
		AppUser appUser = userService
				.getAppUserByUsername(SecurityContextHolder.getContext()
						.getAuthentication().getName());

		ResponseWrapperDesignDecision response = new ResponseWrapperDesignDecision();
		response.setDesignDecision(decision);

		// if user == owner
		if (appUser.equals(decision.getIssue().getOwner())) {
			response.setOwner(true);
			response.setEditable(true);
			// TODO if status == collecting alternatives show "start ranking"
			if (DesignDecisionStatus.COLLECTING_ALTERNATIVES.equals(decision
					.getDesignDecisionStatus())
					&& !decision.getAlternatives().isEmpty()) {
				response.setShowStartRanking(true);
			} else if (DesignDecisionStatus.SELECTING_ALTERNATIVES
					.equals(decision.getDesignDecisionStatus())) {
				// after all shareholders have finished ranking
				response.setShowSelectAlternative(true);
			}

		}

		// if user == shareholder

		for (Share shareHolder : decision.getShares()) {

			if (shareHolder.getAppUser().equals(appUser)) {
				response.setEditable(true);
				response.setIsShareholder(true);
				if (DesignDecisionStatus.RANK_ALTERNATIVES.equals(decision
						.getDesignDecisionStatus())) {
					
					boolean allAlternativesRanked = true;
					for(Alternative alternative : decision.getAlternatives()) {
						for(Share share : decision.getShares()) {
							if(share.getAppUser().equals(appUser) && !alternativeRankingDao.existsRanking(alternative, share)) {
								allAlternativesRanked =  false;
								break;
							}		
						}
					}
					//show button if not alternatives are not ranked
					if (!allAlternativesRanked) {
						response.setShowFinishRanking(true);
					}
					
				}
			}
		}
		
		
		
		

		if (DesignDecisionStatus.DECIDED.equals(decision.getDesignDecisionStatus())) {
			response.setShowDecided(true);
			for (DesignDecisionRating rating : decision.getDesignDecisionRatings()) {
				 if (appUser.equals(rating.getRater().getAppUser())) {
					 response.setRated(true);
					 break;
				 }
			}
			
			
		} else if (DesignDecisionStatus.INAPPROPRIATE_SOLUTION.equals(decision.getDesignDecisionStatus())) {
			response.setShowInappropriateSolution(true);
		} else if (DesignDecisionStatus.OBSOLETE.equals(decision.getDesignDecisionStatus())) {
			response.setShowObsolete(true);
		}
		return response;

	}

	@Override
	@Transactional(readOnly = false)
	public void rateDesignDecision(long id, Integer value, String message, String ratingTime) {
		DesignDecisionRating designDecisionRating = new DesignDecisionRating();
		DesignDecision designDecision = designDecisionDao
				.getDesignDecisionWithRelations(id);

		Comment ratingComment = null;
		if (message != null && ratingTime != null) {
			ratingComment = commentHelper.createComment(message, ratingTime);
			ratingComment.setDesignDecisionRating(designDecisionRating);
			designDecisionRating.getComments().add(ratingComment);
		}

		AppUser appUser = userService.getAppUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		designDecisionRating.setRating(value);
		designDecisionRating.setDesignDecision(designDecision);
		designDecisionRating.setRater(shareDao.getShare(appUser, designDecision));

		if (ratingComment != null) {
			commentDao.saveOrUpdateComment(ratingComment);
		}
		designDecisionRatingDao.saveOrUpdateDesignDecisionRating(designDecisionRating);
	}

	@Override
	@Transactional(readOnly = false)
	public void addSolution(long idDesignDecision, long idSolutionAlternative) {

		DesignDecision designDecision = getDesignDecision(idDesignDecision,
				false).getDesignDecision();

		Alternative solution = alternativeService.getAlternative(
				idSolutionAlternative, false);

		if (designDecision == null || solution == null) {
			return;
		}

		// Only able to set Solution, when in SELECTING_ALTERNATIVES status
		if (!designDecision.getDesignDecisionStatus().equals(
				DesignDecision.DesignDecisionStatus.SELECTING_ALTERNATIVES)) {
			return;
		}

		// Only when the DD has the solution Alternative as possibility
		if (!designDecision.getAlternatives().contains(solution)) {
			return;
		}

		designDecision.setSolution(solution);
		designDecision
				.setDesignDecisionStatus(DesignDecision.DesignDecisionStatus.DECIDED);
		
		setIssueStatus(designDecision.getIssue(), designDecision.getDesignDecisionStatus());
	}
}
