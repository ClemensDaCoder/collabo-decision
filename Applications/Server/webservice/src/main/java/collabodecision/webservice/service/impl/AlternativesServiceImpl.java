package collabodecision.webservice.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.service.AlternativeService;
import collabodecision.webservice.service.utils.CommentHelper;

public class AlternativesServiceImpl implements AlternativeService
{

	CommentDao commentDao;
	AlternativeDao alternativeDao;
	CommentHelper commentHelper;
	
	@Override
	@Transactional(readOnly = false)
	public void addComment(long id, String message, String date) {
		// TODO Auto-generated method stub
		
		Comment comment = commentHelper.getComment(message, date);
		comment.setIssue(issueDao.getIssue(id));
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
	public Alternative getAlternative(long id, boolean withRelations) {
		// TODO Auto-generated method stub
		return null;
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
