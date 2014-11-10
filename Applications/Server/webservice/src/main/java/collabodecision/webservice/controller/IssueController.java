package collabodecision.webservice.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.persistence.IssueDao;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.Issue;

@RestController
@RequestMapping("/issues")
@Transactional
public class IssueController {
	
	@Autowired
	private IssueDao issueDao;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Issue> getIssues() {
		return issueDao.getIssues();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public Issue getIssue(@PathVariable long id, @RequestParam(value="withRelations", defaultValue="false") boolean withRelations) {
		
		if(withRelations) {
			return issueDao.getIssueWithRelations(id);
		}
		
		return issueDao.getIssue(id);
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST)
	public void addIssue(@Valid Issue issue) {
		issueDao.saveOrUpdateIssue(issue);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public void updateIssue(@PathVariable long id, @Valid  Issue issue) {
		issueDao.saveOrUpdateIssue(issue);
	}
	
	/*
	 * Cannot delete or update a parent row: a foreign key constraint fails
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void deleteIssue(@PathVariable long id, @Valid Issue issue) {
		issueDao.deleteIssue(id);
	}
	
	/*
	 * To Do!!!
	 */
	@RequestMapping(value="/{id}/comments", method=RequestMethod.POST)
	public void addComments(@PathVariable long id, @Valid  Set<Comment> comments) {
		issueDao.addComments(id, comments);
	}
}
