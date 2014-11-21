package collabodecision.webservice.service.impl;

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
		Comment comment = commentHelper.getComment(message, date);
		comment.setParentComment(commentDao.getComment(id));
		commentDao.saveOrUpdateComment(comment);
	}

}
