package collabodecision.webservice.service.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.data.RequestWrapperAlternative;
import collabodecision.webservice.data.ResponseWrapperAlternative;
import collabodecision.webservice.persistence.AlternativeDao;
import collabodecision.webservice.persistence.AlternativeRankingDao;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.DesignDecisionDao;
import collabodecision.webservice.persistence.ShareDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.File;
import collabodecision.webservice.service.AlternativeService;
import collabodecision.webservice.service.AppUserService;
import collabodecision.webservice.service.utils.CommentHelper;

@Service
public class AlternativesServiceImpl implements AlternativeService {
	@Autowired
	private CommentDao commentDao;

	@Autowired
	private AlternativeDao alternativeDao;

	@Autowired
	private ShareDao shareDao;
	
	@Autowired
	private AlternativeRankingDao alternativeRankingDao;

	@Autowired
	private CommentHelper commentHelper;

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private AppUserService userService;

	@Autowired
	private DesignDecisionDao designDecisionDao;

	@Override
	@Transactional(readOnly = false)
	public void addComment(long id, String message, String date) {
		Comment comment = commentHelper.createComment(message, date);
		Alternative alternative = alternativeDao.getAlternativeWithRelations(id);
		comment.setAlternative(alternative);
		alternative.getComments().add(comment);
		commentDao.saveOrUpdateComment(comment);
		alternativeDao.saveOrUpdateAlternative(alternative);
	}

	@Override
	public void addFile(long id, String pathToFile) {

	}

	@Override
	@Transactional(readOnly = true)
	public Alternative getAlternative(long id, boolean withRelations) {
		if (withRelations) {
			return alternativeDao.getAlternativeWithRelations(id);
		}

		return alternativeDao.getAlternative(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAlternative(long id) {
		alternativeDao.deleteAlternative(id);

	}

	@Override
	@Transactional(readOnly = false)
	public void addAlternative(RequestWrapperAlternative alternativeRequest) {
		addorUpdateAlternative(alternativeRequest, null);
	}
	
	@Override
	public List<Comment> getChildComments(long idComment) {
		return commentDao.getChildComments(idComment);
	}

	private void addorUpdateAlternative(
			RequestWrapperAlternative alternativeRequest,
			Long idexistingAlternative) {
		Alternative alternative = null;
		if (idexistingAlternative == null) {
			alternative = new Alternative();
		} else {
			alternative = alternativeDao.getAlternative(idexistingAlternative);
		}

		// On Update - Delete all OneToMany Relations in advance (are added
		// again later)
		if (idexistingAlternative != null) {
			alternative.getFiles().clear();
			alternative.getAlternativeFromRelations().clear();
			alternative.getAlternativeToRelations().clear();

			// Must be done - Otherwise Hibernate would result in Violation
			// Constraint!
			sessionFactory.getCurrentSession().flush();
		}

		// Setting the properties of the DesignDecision
		alternative.setDescription(alternativeRequest.getDescription());
		alternative.setDesignDecision(designDecisionDao
				.getDesignDecision(alternativeRequest.getIdDesignDecision()));

		// Set the creator to the currently authenticated user
		AppUser creator = userService
				.getAppUserByUsername(SecurityContextHolder.getContext()
						.getAuthentication().getName());
		alternative.setCreator(creator);

		// Adding new Alternative Files to the Alternative
		if (alternativeRequest.getFiles() != null) {
			for (String file : alternativeRequest.getFiles()) {
				alternative.getFiles().add(new File(file, alternative));
			}
		}

		// this alternative has From Relations

		if (alternativeRequest.getIdAlternativeFromsRelations() != null
				&& alternativeRequest.getIdAlternativeFromsRelations()
						.isEmpty()) {
			List<Alternative> alternativeFromRelations = alternativeDao
					.getAlternativeByIds(alternativeRequest
							.getIdAlternativeFromsRelations());
			for (Alternative alternativeFromRelation : alternativeFromRelations) {
				// TODO: Relations
			}

		}

		// This alternative has to Relations.

		// This issue depends on other issues
		// Only needed when new (not update)
		if (idexistingAlternative == null) {

			alternativeDao.saveOrUpdateAlternative(alternative);
		}
	}

	@Transactional(readOnly = true)
	public ResponseWrapperAlternative getAlternativeWrapped(long id, boolean withRelations) {
		Alternative alternative = getAlternative(id, withRelations); 

		AppUser appUser = userService.getAppUserByUsername(SecurityContextHolder.getContext()
						.getAuthentication().getName());

		ResponseWrapperAlternative response = new ResponseWrapperAlternative();
		response.setAlternative(alternative);
		
		if (alternative.getCreator().equals(appUser)) {
			response.setEditable(true);
		}

		return response;
	}

	@Override
	@Transactional(readOnly = false)
	public void updateAlternative(long existingid, RequestWrapperAlternative alternative) {
		addorUpdateAlternative(alternative, existingid);

	}
}
