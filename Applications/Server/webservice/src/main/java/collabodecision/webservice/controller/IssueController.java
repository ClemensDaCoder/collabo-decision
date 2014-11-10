package collabodecision.webservice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
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
import collabodecision.webservice.persistence.domain.User;

@RestController
@RequestMapping("/issues")
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

	@RequestMapping(method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public List<Issue> getIssues(@RequestParam(value = "status") String status,
			@RequestParam(value = "tags") List<String> tags) {
		IssueStatus issueStatus = issueStatusDao.getIssueStatusByName(status);
		List<Tag> tagsOfIssue = tagDao.getTagsByName(tags);
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
	public void addIssue(
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "owner", required = true) long idOwner,
			@RequestParam(value = "tags") List<String> tagNames,
			@RequestParam(value = "files") List<String> files,
			@RequestParam(value = "depends") List<Long> dependIds,
			@RequestParam(value = "resolves") List<Long> resolvesIds,
			@RequestParam(value = "related") List<Long> relatedIds) {

		Issue issue = new Issue();

		// Setting the properties of the Issue
		issue.setTitle(title);
		issue.setDescription(description);
		issue.setIssueStatus(issueStatusDao.getIssueStatusByName("NEW"));
		issue.setOwner(userDao.getUser(idOwner));

		// Set the creator to the currently authenticated user
		User creator = userDao.getUserByUsername(SecurityContextHolder
				.getContext().getAuthentication().getName());
		issue.setCreator(creator);

		// Adding the tags - Might be new ones there
		List<Tag> tags = tagDao.saveOrUpdateTags(tagNames);

		// Adding the IssueTags to the Issue
		Set<IssueTag> issueTags = new HashSet<>();
		for (Tag tag : tags) {
			issueTags.add(new IssueTag(tag, issue));
		}
		issue.setIssueTags(issueTags);

		// Adding new Issue Files to the Issue
		Set<File> issueFiles = new HashSet<>();
		for (String file : files) {
			issueFiles.add(new File(file, issue));
		}
		issue.setFiles(issueFiles);

		// Adding Related Issues to Issue
		Set<IssueRelation> issueRelationsFrom = new HashSet<>();
		Set<IssueRelation> issueRelationsTo = new HashSet<>();

		// This issue depends on other issues
		List<Issue> dependingIssues = issueDao.getIssuesByIds(dependIds);
		for (Issue dependingIssue : dependingIssues) {
			issueRelationsFrom.add(new IssueRelation(issue, dependingIssue,
					relationTypeDao.getRelationTypeByType("DEPENDS")));
		}

		// This issue resolves other issues
		List<Issue> resolvesIssues = issueDao.getIssuesByIds(resolvesIds);
		for (Issue resolvesIssue : resolvesIssues) {
			issueRelationsFrom.add(new IssueRelation(issue, resolvesIssue,
					relationTypeDao.getRelationTypeByType("RESOLVES")));
		}

		// This issue is related to other issues
		// other issues are related to this issue
		List<Issue> relatedIssues = issueDao.getIssuesByIds(relatedIds);
		for (Issue relatedIssue : relatedIssues) {
			issueRelationsFrom.add(new IssueRelation(issue, relatedIssue,
					relationTypeDao.getRelationTypeByType("RELATES")));
			issueRelationsTo.add(new IssueRelation(relatedIssue, issue,
					relationTypeDao.getRelationTypeByType("RELATES")));
		}

		issue.setIssueRelationsFrom(issueRelationsFrom);
		issue.setIssueRelationsTo(issueRelationsTo);
		
		// If the issue depends on another issue -> it is considered blocked!
		issue.setBlocked(dependingIssues.size() > 0);

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
}
