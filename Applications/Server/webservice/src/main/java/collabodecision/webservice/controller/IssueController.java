package collabodecision.webservice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.IssueDao;
import collabodecision.webservice.persistence.UserDao;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.Issue;

@RestController
@RequestMapping("/issues")
@Transactional
public class IssueController {

	@Autowired
	private IssueDao issueDao;

	@RequestMapping(method = RequestMethod.GET)
	public List<Issue> getIssues(@RequestParam(value = "status") String status,
			@RequestParam(value = "tags") String tags) {
		
		return null;
//		return issueDao.getIssues(status, tags);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Issue getIssue(
			@PathVariable long id,
			@RequestParam(value = "withRelations", defaultValue = "false") boolean withRelations) {

		if (withRelations) {
			return issueDao.getIssueWithRelations(id);
		}

		return issueDao.getIssue(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void addIssue(@Valid Issue issue) {
		issueDao.saveOrUpdateIssue(issue);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateIssue(@PathVariable long id, @Valid Issue issue) {
		issueDao.saveOrUpdateIssue(issue);
	}

	/*
	 * Cannot delete or update a parent row: a foreign key constraint fails
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteIssue(@PathVariable long id, @Valid Issue issue) {
		issueDao.deleteIssue(id);
	}


	@Transactional(readOnly = false)
	@RequestMapping(value = "/{idIssue}", method = RequestMethod.POST)
	public void addComment(@PathVariable long idIssue,
			@RequestParam(value = "message") String message,
			@RequestParam(value = "date") String stringDate) {

		Comment comment = new Comment();
		comment.setIssue(issueDao.getIssue(idIssue));
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
