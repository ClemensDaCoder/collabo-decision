package collabodecision.webservice.service.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.data.RequestWrapperAlternative;
import collabodecision.webservice.persistence.AlternativeDao;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.File;
import collabodecision.webservice.service.AlternativeService;
import collabodecision.webservice.service.AppUserService;
import collabodecision.webservice.service.utils.CommentHelper;

@Service
public class AlternativesServiceImpl implements AlternativeService
{
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private AlternativeDao alternativeDao;
	
	@Autowired
	private CommentHelper commentHelper;
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private AppUserService userService;
	
	@Override
	@Transactional(readOnly = false)
	public void addComment(long id, String message, String date) {
		// TODO Auto-generated method stub
		
		Comment comment = commentHelper.getComment(message, date);
		
		// AlternativeDao wird so eine funkion brauchen.
		comment.setAlternative(alternativeDao.getAlternative(id));
		commentDao.saveOrUpdateComment(comment);
		
	}

	@Override
	public void addFile(long id, String pathToFile) {
		// TODO Auto-generated method stub
		
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
	public void deleteAlternative(long id) {
		alternativeDao.deleteAlternative(id);
		
	}

	@Override
	public void addAlternative(Alternative alternative) {
		// TODO Auto-generated method stub
		addOrUpdateAlternative(alternative, null);
	}

	@Override
	public void updateAlternative(long id, Alternative alternative) {
		// TODO Auto-generated method stub
		addOrUpdateAlternative(alternative, id);
	}

	@Override
	public void rankAlternative(long id, long ranker, String rank) {
		// TODO Auto-generated method stub
		
	}
	
	private void addOrUpdateAlternative(Alternative alternative,
			Long idExistingIssue) {
	}

	@Override
	public void addRequestWrapperAlternative(
			RequestWrapperAlternative alternativeRequest) {
		// TODO Auto-generated method stub
		addorUpdateAlternative(alternativeRequest,null);
		
	}
	
	public void addorUpdateAlternative(RequestWrapperAlternative alternativeRequest, Long idexistingAlternative)
	{
		Alternative alternative = null;
		if(idexistingAlternative == null)
		{
			alternative = new Alternative();
		}
		else
		{
			alternativeDao.getAlternative(idexistingAlternative);
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

		//this alternative has From Relations
		
		
		if(alternativeRequest.getIdAlternativeFromsRelations() != null && alternativeRequest.getIdAlternativeFromsRelations().isEmpty())
		{
			List<Alternative> alternativeFromRelations = alternativeDao.getAlternativeByIds(alternativeRequest.getIdAlternativeFromsRelations());
			for(Alternative alternativeFromRelation : alternativeFromRelations)
			{
				
			}
			
		}
		
		
		//This alternative has to Relations.
		
		
		// This issue depends on other issues
		// Only needed when new (not update)
		if (idexistingAlternative == null) {
			
			alternativeDao.saveOrUpdateAlternative(alternative);
		}
	}
	



}
