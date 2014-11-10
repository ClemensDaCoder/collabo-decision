package collabodecision.webservice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.DesignDecisionDao;
import collabodecision.webservice.persistence.UserDao;

@RestController
@RequestMapping("/designdecisions")
@Transactional
public class DesignDecisionController {
	
	@Autowired
	private DesignDecisionDao designDecisionDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CommentDao commentDao;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<DesignDecision> getDesignDecisions() {
		return designDecisionDao.getDesignDecisions();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public DesignDecision getDesignDecision(@PathVariable long id, @RequestParam(value="withRelations", defaultValue="false") boolean withRelations) {
		
		if(withRelations) {
			return designDecisionDao.getDesignDecisionWithRelations(id);
		}
		
		return designDecisionDao.getDesignDecision(id);
	}	
	
	/*
	 * Column 'assumption' cannot be null
	 */
	@RequestMapping(method=RequestMethod.POST)
	public void addDesignDecision(@Valid DesignDecision designDecision) {
		designDecisionDao.saveOrUpdateDesignDecision(designDecision);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public void updateDesignDecision(@PathVariable long id, @Valid  DesignDecision designDecision) {
		designDecisionDao.saveOrUpdateDesignDecision(designDecision);
	}
	
	/*
	 * Cannot delete or update a parent row: a foreign key constraint fails
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void deleteDesignDecision(@PathVariable long id, @Valid DesignDecision sesignDecision) {
		designDecisionDao.deleteDesignDecision(id);
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "/{idDesignDecision}", method = RequestMethod.POST)
	public void addComment(@PathVariable long idDesignDecision,
			@RequestParam(value = "message") String message,
			@RequestParam(value = "date") String stringDate) {

		Comment comment = new Comment();
		comment.setDesignDecision(designDecisionDao.getDesignDecision(idDesignDecision));
		comment.setText(message);
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(stringDate);
			comment.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Fetch the username from the SecurityContext
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		comment.setCreator(userDao.getUserByUsername(username));

		commentDao.saveOrUpdateComment(comment);
	}
}
