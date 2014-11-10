package collabodecision.webservice.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.UserDao;
import collabodecision.webservice.persistence.domain.Comment;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = false)
	@RequestMapping(value = "/{idParentComment}", method = RequestMethod.POST)
	public void addComment(@PathVariable long idParentComment,
			@RequestParam(value = "message") String message,
			@RequestParam(value = "date") Date date) {

		Comment comment = new Comment();
		comment.setParentComment(commentDao.getComment(idParentComment));
		comment.setText(message);
		comment.setDate(date);
		
		// Fetch the username from the SecurityContext
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		comment.setCreator(userDao.getUserByUsername(username));

		commentDao.saveOrUpdateComment(comment);
	}
}
