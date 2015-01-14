package collabodecision.webservice.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.data.RequestWrapperDesignDecision;
import collabodecision.webservice.data.RequestWrapperRankAlternatives;
import collabodecision.webservice.data.ResponseWrapperDesignDecision;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.DesignDecisionDao;
import collabodecision.webservice.persistence.DesignDecisionRatingDao;
import collabodecision.webservice.persistence.ShareDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.AlternativeRanking;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.domain.DesignDecisionRating;
import collabodecision.webservice.persistence.domain.DesignDecision.DesignDecisionStatus;
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
	
	
	@Override
	public List<ResponseWrapperDesignDecision> getDesignDecisions(String status, boolean isShareholder, boolean toRank, boolean toRate) {
		DesignDecisionStatus decisionstatus = status != null ? DesignDecisionStatus
				.valueOf(status) : null;

		List<ResponseWrapperDesignDecision> responses = new ArrayList<>();
		for (DesignDecision decision : designDecisionDao
				.getDesignDecisions(decisionstatus, isShareholder, toRank, toRate)) {
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
	public ResponseWrapperDesignDecision getDesignDecision(long id,
			boolean withRelations) {
		if (withRelations) {
			DesignDecision decision = calculateRanking(designDecisionDao
					.getDesignDecisionWithRelations(id));
			
			
			return wrapDesignDecision(designDecisionDao
					.getDesignDecisionWithRelations(id));
			
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
					.setDesignDecisionStatus(DesignDecision.DesignDecisionStatus
							.valueOf(designDecisionRequest
									.getDesignDecisionStatus()));

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
		DesignDecision designDecision = designDecisionDao.getDesignDecisionWithRelations(id);
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

	private void addOrUpdateDesignDecision(RequestWrapperDesignDecision decisionRequest, Long idExistingDesignDecision) {
		
		// get DesignDecision from DB if update; otherwise new Design Decion
		DesignDecision decision = idExistingDesignDecision != null ? designDecisionDao.getDesignDecision(idExistingDesignDecision) : new DesignDecision();

		if (idExistingDesignDecision != null) {
			decision.getShares().clear();
//			decision.getAlternatives().clear();
//			decision.getComments().clear();
			decision.getFiles().clear();
			
			// Must be done - Otherwise Hibernate would result in Violation Constraint!
			sessionFactory.getCurrentSession().flush();
		} else {
			decision.setCreationDate(new Date(System.currentTimeMillis()));
		}

		// Setting the properties of the DesignDecision
		decision.setTitle(decisionRequest.getTitle());
		decision.setAssumption(decisionRequest.getAssumption());

		decision.setIssue(issueService.getIssue(decisionRequest.getIdIssue(),
				false).getIssue());

		// set shareholders
		Set<Share> shareholders = new HashSet<Share>();
		for (Long appUserId : decisionRequest.getAppUserIds()) {
			AppUser appUser = userService.getAppUser(appUserId);
			
			// Only check for existing shares when Update
			if(idExistingDesignDecision != null) {
				Share share = shareDao.getShare(appUser, decision);
				shareholders.add(share != null ? share : new Share(appUser, decision));
			}
			
			shareholders.add(new Share(appUser, decision));
		}

		decision.setShares(shareholders);

		decision.setRationale(decisionRequest.getRationale());

		decision.setDesignDecisionStatus(DesignDecisionStatus
				.valueOf(decisionRequest.getDesignDecisionStatus()));

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
	private DesignDecision calculateRanking(DesignDecision decision)
	{
		int[] values = new int[decision.getAlternatives().size()];
		int i = 0;
		for(Alternative a: decision.getAlternatives())
		{
		
			int sum = 0;
			for(AlternativeRanking ar: a.getAlternativeRankings())
			{
				sum += ar.getRank();
			}
			a.setRankingpoints(sum);
			values[i] = sum;
			i++;
		}
		Arrays.sort(values);
		for(Alternative a: decision.getAlternatives())
		{
			for(int j = 0; j < values.length; j++)
			{
				if(a.getRankingpoints() == values[j])
				{
					a.setRanking(j + 1);
				}
			}
		}
		
		return decision;
	}

	private ResponseWrapperDesignDecision wrapDesignDecision(
			DesignDecision decision) {

		//set Alternative Data
		//decision= calculateRanking(decision);

		
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
		
		for(Share shareHolder : decision.getShares()) {
			
			if(shareHolder.getAppUser().equals(appUser)) {
				response.setEditable(true);
				response.setIsShareholder(true);
				if (DesignDecisionStatus.RANK_ALTERNATIVES.equals(decision
						.getDesignDecisionStatus())) {
					response.setShowFinishRanking(true);
				}
			}
			
		}
		
		// Cannot check ShareHolder against AppUser -> returns null
//		if (decision.getShareHolders().contains(appUser)) {
//			response.setEditable(true);
//			response.setIsShareholder(true);
//			if (DesignDecisionStatus.RANK_ALTERNATIVES.equals(decision
//					.getDesignDecisionStatus())) {
//				response.setShowFinishRanking(true);
//			}
//		}

		if (DesignDecisionStatus.DECIDED.equals(decision
				.getDesignDecisionStatus())) {
			response.setShowDecided(true);
		} else if (DesignDecisionStatus.INAPPROPRIATE_SOLUTION.equals(decision
				.getDesignDecisionStatus())) {
			response.setShowInappropriateSolution(true);
		} else if (DesignDecisionStatus.OBSOLETE.equals(decision
				.getDesignDecisionStatus())) {
			response.setShowObsolete(true);
		}
		return response;

	}


	@Override
	public void rankDesignDecision(long idDesignDecision,
			RequestWrapperRankAlternatives requestWrapperRankAlternatives) {
		
		HashMap<Long, Integer> map = requestWrapperRankAlternatives.getMap();
		for(long id : map.keySet())
		{
			alternativeService.rankAlternative(id, map.get(id));
		}
		
		DesignDecision d = designDecisionDao.getDesignDecision(idDesignDecision);
		d.getShares();
		List<Share> l = new ArrayList<Share>();
		for(Alternative a: d.getAlternatives())
		{
			
			for(AlternativeRanking ar: a.getAlternativeRankings())
			{
				l.add(ar.getShare());
			}
		}
		Collection<Share> collection = d.getShares();
		
		if(l.containsAll(collection))
		{
			d.setDesignDecisionStatus(DesignDecisionStatus.SELECTING_ALTERNATIVES);
			System.out.print("Ready to select alternative");
			//fertig mit Ranken
		}
		else
		{
			System.out.print("Not finished");
			//noch nicht fertig
		}
	
	}

	@Override
	@Transactional(readOnly = false)

	public void rateDesignDecision(long id, Integer value, String message, String ratingTime) {
		// TODO Auto-generated method stub
		DesignDecisionRating designDecisionRating = new DesignDecisionRating();
		DesignDecision designDecision = designDecisionDao.getDesignDecisionWithRelations(id);

		if(message !=  null && ratingTime != null)
		{
		Comment ratingComment = commentHelper.createComment(message, ratingTime);
		ratingComment.setDesignDecisionRating(designDecisionRating);
		designDecisionRating.getComments().add(ratingComment);
		
		commentDao.saveOrUpdateComment(ratingComment);
		//alternativeDao.saveOrUpdateAlternative(alternative);
		}
		
		
		//commentDao.saveOrUpdateComment(comment);
		//designDecisionDao.saveOrUpdateDesignDecision(designDecision);
		
		//DesignDecision decision = designDecisionDao.getDesignDecision(id);

		AppUser appUser = userService
				.getAppUserByUsername(SecurityContextHolder.getContext()
						.getAuthentication().getName());		
		designDecisionRating.setRating(value);
		designDecisionRating.setDesignDecision(designDecision);
		designDecisionRating.setRater(shareDao.getShare(appUser, designDecision));		
		designDecisionRatingDao.saveOrUpdateDesignDecisionRating(designDecisionRating);
				
	}
}
