package collabodecision.webservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.AlternativeDao;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.Issue;
import collabodecision.webservice.service.AlternativeService;
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
	public List<Alternative> getAlternatives(String status, List<String> tags) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAlternative(Alternative alternative) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAlternative(long id, Alternative alternative) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rankAlternative(long id, long ranker, String rank) {
		// TODO Auto-generated method stub
		
	}

}
