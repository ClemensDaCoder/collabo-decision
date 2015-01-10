package collabodecision.webservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.service.CommentService;
import collabodecision.webservice.service.utils.CommentHelper;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentHelper commentHelper;
	
	@Autowired
	private CommentDao commentDao;
	
	
	@Override
	@Transactional(readOnly = false)
	public void addComment(long id, String message, String date) {
		Comment comment = commentHelper.createComment(message, date);
		comment.setParentComment(commentDao.getComment(id));
		commentDao.saveOrUpdateComment(comment);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Comment> getChildComments(long idComment) {
		return commentDao.getChildComments(idComment);
	}

}
