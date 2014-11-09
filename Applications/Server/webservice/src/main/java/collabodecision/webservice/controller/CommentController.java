package collabodecision.webservice.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.domain.Comment;

@RestController
@RequestMapping("/comments")
@Transactional
public class CommentController {

	@Autowired
	private CommentDao commentDao;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Comment> getComments() {
		return commentDao.getComments();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public Comment getComment(@PathVariable long id, @RequestParam(value="withRelations", defaultValue="false") boolean withRelations) {
		
		if(withRelations) {
			return commentDao.getCommentWithRelations(id);
		}
		
		return commentDao.getComment(id);
	}	
	
	/*
	 * Column 'idCreator' cannot be null
	 */
	@RequestMapping(method=RequestMethod.POST)
	public void addComment(@Valid Comment comment) {
		commentDao.saveOrUpdateComment(comment);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public void updateComment(@PathVariable long id, @Valid  Comment comment) {
		commentDao.saveOrUpdateComment(comment);
	}
	
	/*
	 * Works!
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void deleteComment(@PathVariable long id, @Valid Comment comment) {
		commentDao.deleteComment(id);
	}

}
