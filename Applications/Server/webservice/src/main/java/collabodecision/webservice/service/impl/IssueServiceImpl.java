package collabodecision.webservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.data.RequestWrapperIssue;
import collabodecision.webservice.data.ResponseWrapperIssue;
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
	public List<ResponseWrapperIssue> getResponseWrapperIssues(String status,
			List<String> tags, String partialTitle) {
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

		List<ResponseWrapperIssue> result = new ArrayList<>();

		for (Issue issue : issueDao.getIssues(issueStatus, tagsOfIssue,
				partialTitle)) {

			ResponseWrapperIssue rwi = new ResponseWrapperIssue();
			rwi.setIssue(issue);

			setResponseWrapperIssueFlags(rwi);

			result.add(rwi);
		}

		return result;
	}
	
	

	@Override
	@Transactional(readOnly = true)
	public ResponseWrapperIssue getResponseWrapperIssue(long id,
			boolean withRelations) {

		Issue issue = withRelations ? issueDao.getIssueWithRelations(id)
				: issueDao.getIssue(id);;

		ResponseWrapperIssue rwi = new ResponseWrapperIssue();
		rwi.setIssue(issue);
		
		setResponseWrapperIssueFlags(rwi);
		
		return rwi;

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
		Issue issue = idExistingIssue != null ? issueDao.getIssue(idExistingIssue) : new Issue();

		// On Update - Delete all OneToMany Relations in advance (are added
		// again later)
		if (idExistingIssue != null) {
			issue.getIssueTags().clear();
			issue.getIssueRelationsFrom().clear();
			issue.getIssueRelationsTo().clear();
			issue.getFiles().clear();
			issue.getDependingIssues().clear();
			issue.getDependsIssues().clear();
			issue.getResolvedByIssues().clear();
			issue.getResolvesIssues().clear();
			issue.getRelatedIssues().clear();

			// Must be done - Otherwise Hibernate would result in Violation
			// Constraint!
			sessionFactory.getCurrentSession().flush();
		}

		// Setting the properties of the Issue
		issue.setTitle(issueRequest.getTitle());
		issue.setDescription(issueRequest.getDescription());

		// Set Issue status
		issue.setIssueStatus(issueStatusDao.getIssueById(issueRequest
				.getIdIssueStatus()));
		issue.setOwner(userService.getAppUser(issueRequest.getIdOwner()));

		// Set the creator to the currently authenticated user
		AppUser creator = userService
				.getAppUserByUsername(SecurityContextHolder.getContext()
						.getAuthentication().getName());
		issue.setCreator(creator);

		// Get all the tags that are already in the DB
		List<Tag> tagsInDb = tagDao.getTagsByName(issueRequest.getTags());

		for (String tagName : issueRequest.getTags()) {
			Tag newTag = new Tag(tagName);
			// Adding non existing Tags to the DB
			if (!tagsInDb.contains(newTag)) {
				tagDao.saveOrUpdateTag(newTag);
				tagsInDb.add(newTag);
			}
		}

		// Adding the IssueTags to the Issue
		for (Tag tag : tagsInDb) {
			issue.getIssueTags().add(new IssueTag(tag, issue));
		}

		// Adding new Issue Files to the Issue
		if (issueRequest.getFiles() != null) {
			for (String file : issueRequest.getFiles()) {
				issue.getFiles().add(new File(file, issue));
			}
		}

		// This issue depends on other issues
		if (issueRequest.getIdsDepends() != null
				&& !issueRequest.getIdsDepends().isEmpty()) {
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
		if (issueRequest.getIdsResolves() != null
				&& !issueRequest.getIdsResolves().isEmpty()) {
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
		if (issueRequest.getIdsRelates() != null
				&& !issueRequest.getIdsRelates().isEmpty()) {
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
		
		setRelatedIssues(issue, issueRequest);

		// Only needed when new (not update)
		if (idExistingIssue == null) {
			issueDao.saveOrUpdateIssue(issue);
		}
	}
	
	private void setRelatedIssues(Issue issue, RequestWrapperIssue issueRequest) {
		for (Long relatedIssueIds :issueRequest.getIdsRelates())  {
			Issue relatedIssue = issueDao.getIssue(relatedIssueIds);
			issue.getRelatedIssues().add(relatedIssue);
			relatedIssue.getRelatedIssues().add(issue);
		}
	}

	/**
	 * Help method for setting the ResponswWrapperFlags according to the AppUser
	 * Roles
	 * 
	 * @param rwi
	 *            The ResponseWrapperIssue instance - Issue must be set before
	 *            this invocation
	 */
	private void setResponseWrapperIssueFlags(ResponseWrapperIssue rwi) {

		AppUser appUser = userService
				.getAppUserByUsername(SecurityContextHolder.getContext()
						.getAuthentication().getName());

		// Editable only if Owner or Creator
		boolean isEditable = rwi.getIssue().getOwner().equals(appUser)
				|| rwi.getIssue().getCreator().equals(appUser);

		rwi.setEditable(isEditable);
		
		rwi.setOwner(rwi.getIssue().getOwner().equals(appUser));

		// TODO: check if needed in list (probably not)
		rwi.setShowInProgress(false);
		rwi.setShowObsolete(false);
		rwi.setShowRepeat(false);
		rwi.setShowResolved(false);
	}

}
