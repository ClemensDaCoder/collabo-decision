package collabodecision.webservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.data.RequestWrapperIssue;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.IssueDao;
import collabodecision.webservice.persistence.IssueStatusDao;
import collabodecision.webservice.persistence.RelationTypeDao;
import collabodecision.webservice.persistence.TagDao;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.File;
import collabodecision.webservice.persistence.domain.Issue;
import collabodecision.webservice.persistence.domain.IssueRelation;
import collabodecision.webservice.persistence.domain.IssueStatus;
import collabodecision.webservice.persistence.domain.IssueTag;
import collabodecision.webservice.persistence.domain.Tag;
import collabodecision.webservice.service.AppUserService;
import collabodecision.webservice.service.IssueService;
import collabodecision.webservice.service.utils.CommentHelper;

@Service
public class IssueServiceImpl implements IssueService {

	@Autowired
	private TagDao tagDao;

	@Autowired
	private IssueStatusDao issueStatusDao;

	@Autowired
	private IssueDao issueDao;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private RelationTypeDao relationTypeDao;

	@Autowired
	private AppUserService userService;

	@Autowired
	private CommentHelper commentHelper;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public List<Issue> getIssues(String status, List<String> tags, String partialTitle) {
		IssueStatus issueStatus = null;

		if (status != null) {
			issueStatus = issueStatusDao.getIssueStatusByName(status);
		}

		List<Tag> tagsOfIssue = null;

		if (tags != null) {
			tagsOfIssue = tagDao.getTagsByName(tags);

			// If Issues with similar tags should be found, but no matching tags
			// were found: means that there cannot possibly be an issue with
			// these tags
			if (tagsOfIssue == null || tagsOfIssue.isEmpty()) {
				return null;
			}
		}
		
		return issueDao.getIssues(issueStatus, tagsOfIssue, partialTitle);
	}

	@Override
	@Transactional(readOnly = true)
	public Issue getIssue(long id, boolean withRelations) {

		if (withRelations) {
			return issueDao.getIssueWithRelations(id);
		}

		return issueDao.getIssue(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteIssue(long id) {
		issueDao.deleteIssue(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void addIssue(RequestWrapperIssue issueRequest) {
		addOrUpdateIssue(issueRequest, null);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateIssue(long id, RequestWrapperIssue issueRequest) {
		addOrUpdateIssue(issueRequest, id);
	}

	@Override
	@Transactional(readOnly = false)
	public void addComment(long id, String message, String date) {
		Comment comment = commentHelper.getComment(message, date);
		comment.setIssue(issueDao.getIssue(id));
		commentDao.saveOrUpdateComment(comment);
	}

	@Override
	public void addFile(long id, String pathToFile) {

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
	private void addOrUpdateIssue(RequestWrapperIssue issueRequest,
			Long idExistingIssue) {

		// Fetching the Issue from DB if Update ; otherwise create new
		Issue issue = idExistingIssue != null ? issueDao
				.getIssue(idExistingIssue) : new Issue();

		// On Update - Delete all OneToMany Relations in advance (are added
		// again later)
		if (idExistingIssue != null) {
			issue.getIssueTags().clear();
			issue.getIssueRelationsFrom().clear();
			issue.getIssueRelationsTo().clear();
			issue.getFiles().clear();

			// Must be done - Otherwise Hibernate would result in Violation
			// Constraint!
			sessionFactory.getCurrentSession().flush();
		}

		// Setting the properties of the Issue
		issue.setTitle(issueRequest.getTitle());
		issue.setDescription(issueRequest.getDescription());

		// TODO: Check issue Status: According to Process! Go with the flow!
		issue.setIssueStatus(issueStatusDao.getIssueStatusByName("NEW"));
		issue.setOwner(userService.getAppUser(issueRequest.getIdOwner()));

		// Set the creator to the currently authenticated user
		AppUser creator = userService
				.getAppUserByUsername(SecurityContextHolder.getContext()
						.getAuthentication().getName());
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
				issue.getIssueRelationsFrom().add(
						new IssueRelation(issue, dependingIssue,
								relationTypeDao
										.getRelationTypeByType("DEPENDS")));
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
				issue.getIssueRelationsFrom().add(
						new IssueRelation(issue, resolvesIssue, relationTypeDao
								.getRelationTypeByType("RESOLVES")));
			}
		}

		// This issue is related to other issues
		// other issues are related to this issue
		if (issueRequest.getIdsRelates() != null) {
			List<Issue> relatedIssues = issueDao.getIssuesByIds(issueRequest
					.getIdsRelates());
			for (Issue relatedIssue : relatedIssues) {
				issue.getIssueRelationsFrom().add(
						new IssueRelation(issue, relatedIssue, relationTypeDao
								.getRelationTypeByType("RELATES")));
				issue.getIssueRelationsTo().add(
						new IssueRelation(relatedIssue, issue, relationTypeDao
								.getRelationTypeByType("RELATES")));
			}
		}

		// Only needed when new (not update)
		if (idExistingIssue == null) {
			issueDao.saveOrUpdateIssue(issue);
		}
	}

}
