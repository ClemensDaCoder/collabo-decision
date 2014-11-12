package collabodecision.webservice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.data.requestWrapper.IssueRequestWrapper;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.IssueDao;
import collabodecision.webservice.persistence.IssueStatusDao;
import collabodecision.webservice.persistence.RelationTypeDao;
import collabodecision.webservice.persistence.TagDao;
import collabodecision.webservice.persistence.UserDao;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.File;
import collabodecision.webservice.persistence.domain.Issue;
import collabodecision.webservice.persistence.domain.IssueRelation;
import collabodecision.webservice.persistence.domain.IssueStatus;
import collabodecision.webservice.persistence.domain.IssueTag;
import collabodecision.webservice.persistence.domain.Tag;
import collabodecision.webservice.persistence.domain.AppUser;

@RestController
@RequestMapping("api/issues")
public class IssueController {

	@Autowired
	private IssueDao issueDao;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private IssueStatusDao issueStatusDao;

	@Autowired
	private TagDao tagDao;

	@Autowired
	private RelationTypeDao relationTypeDao;
	
	@Autowired
	private SessionFactory sessionFactory;

	@RequestMapping(method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public @ResponseBody List<Issue> getIssues(
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "tag", required = false) List<String> tags) {
		
		IssueStatus issueStatus = null;
		
		if(status != null) {
			issueStatus = issueStatusDao.getIssueStatusByName(status);
		}
		
		List<Tag> tagsOfIssue = null;
		
		if(tags != null) {
			tagsOfIssue = tagDao.getTagsByName(tags);
		}
		
		return issueDao.getIssues(issueStatus, tagsOfIssue);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public Issue getIssue(
			@PathVariable long id,
			@RequestParam(value = "withRelations", defaultValue = "false") boolean withRelations) {

		if (withRelations) {
			return issueDao.getIssueWithRelations(id);
		}

		return issueDao.getIssue(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public void addIssue(@RequestBody IssueRequestWrapper request) {
		addOrUpdateIssue(request, null);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@Transactional(readOnly = false)
	public void updateIssue(@PathVariable long id,
			@RequestBody IssueRequestWrapper issueRequest) {
		addOrUpdateIssue(issueRequest, id);
	}

	/*
	 * Cannot delete or update a parent row: a foreign key constraint fails
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@Transactional(readOnly = false)
	public void deleteIssue(@PathVariable long id) {
		issueDao.deleteIssue(id);
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "/{idIssue}/comments", method = RequestMethod.POST)
	public void addComment(@PathVariable long idIssue,
			@RequestParam(value = "message") String message,
			@RequestParam(value = "date") String stringDate) {

		Comment comment = new Comment();
		comment.setIssue(issueDao.getIssue(idIssue));
		comment.setText(message);
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
					.parse(stringDate);
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

	/**
	 * Help method to create a new issue, or to update an existing one
	 * 
	 * @param issueRequest
	 *            The Request for the issue (what data)
	 * @param idExistingIssue
	 *            If update - this Variable must be set. If null: New Issue will
	 *            be created
	 */
	private void addOrUpdateIssue(IssueRequestWrapper issueRequest,
			Long idExistingIssue) {

		// Fetching the Issue from DB if Update ; otherwise create new
		Issue issue = idExistingIssue != null ? issueDao
				.getIssue(idExistingIssue) : new Issue();
				
		// On Update - Delete all OneToMany Relations in advance (are added again later)
		if(idExistingIssue != null) {
			issue.getIssueTags().clear();
			issue.getIssueRelationsFrom().clear();
			issue.getIssueRelationsTo().clear();
			issue.getFiles().clear();
			
			// Must be done - Otherwise Hibernate would result in Violation Constraint!
			sessionFactory.getCurrentSession().flush();
		}
		

		// Setting the properties of the Issue
		issue.setTitle(issueRequest.getTitle());
		issue.setDescription(issueRequest.getDescription());
		
		// TODO: Check issue Status: According to Process! Go with the flow!
		issue.setIssueStatus(issueStatusDao.getIssueStatusByName("NEW"));
		issue.setOwner(userDao.getUser(issueRequest.getIdOwner()));

		// Set the creator to the currently authenticated user
		AppUser creator = userDao.getUserByUsername(SecurityContextHolder
				.getContext().getAuthentication().getName());
		issue.setCreator(creator);

		// Get all the tags that are already in the DB
		List<Tag> tagsInDb = tagDao.getTagsByName(issueRequest.getTags());
		List<Tag> tags = new ArrayList<>();

		for (String tagName : issueRequest.getTags()) {
			Tag newTag = new Tag(tagName);
			// Adding non existing Tags to the DB
			if (tagsInDb == null || !tagsInDb.contains(newTag)) {
				tags.add(newTag);
				tagDao.saveOrUpdateTag(newTag);
			}
		}

		// These are all the Tags of the Issue (from DB and new Ones)
		tags.addAll(tagsInDb);	

		// Adding the IssueTags to the Issue
		for (Tag tag : tags) {
			issue.getIssueTags().add(new IssueTag(tag, issue));
		}

		// Adding new Issue Files to the Issue
		if (issueRequest.getFiles() != null) {
			for (String file : issueRequest.getFiles()) {
				issue.getFiles().add(new File(file, issue));
			}
		}

		// This issue depends on other issues
		if (issueRequest.getIdsDepends() != null) {
			List<Issue> dependingIssues = issueDao.getIssuesByIds(issueRequest
					.getIdsDepends());
			
			for (Issue dependingIssue : dependingIssues) {
				issue.getIssueRelationsFrom().add(new IssueRelation(issue, dependingIssue,
						relationTypeDao.getRelationTypeByType("DEPENDS")));
			}

			// If the issue depends on another issue -> it is considered
			// blocked!
			// TODO: Check only blocks when DD is not accepted!
			issue.setBlocked(dependingIssues.size() > 0);
		}
		// This issue resolves other issues
		if (issueRequest.getIdsResolves() != null) {
			List<Issue> resolvesIssues = issueDao.getIssuesByIds(issueRequest
					.getIdsResolves());
			for (Issue resolvesIssue : resolvesIssues) {
				issue.getIssueRelationsFrom().add(new IssueRelation(issue, resolvesIssue,
						relationTypeDao.getRelationTypeByType("RESOLVES")));
			}
		}

		// This issue is related to other issues
		// other issues are related to this issue
		if (issueRequest.getIdsRelates() != null) {
			List<Issue> relatedIssues = issueDao.getIssuesByIds(issueRequest
					.getIdsRelates());
			for (Issue relatedIssue : relatedIssues) {
				issue.getIssueRelationsFrom().add(new IssueRelation(issue, relatedIssue,
						relationTypeDao.getRelationTypeByType("RELATES")));
				issue.getIssueRelationsTo().add(new IssueRelation(relatedIssue, issue,
						relationTypeDao.getRelationTypeByType("RELATES")));
			}
		}

		// Only needed when new (not update)
		if (idExistingIssue == null) {
			issueDao.saveOrUpdateIssue(issue);
		}
	}
}
