package collabodecision.webservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.data.RequestWrapperIssue;
import collabodecision.webservice.data.ResponseWrapperIssue;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.IssueDao;
import collabodecision.webservice.persistence.TagDao;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.File;
import collabodecision.webservice.persistence.domain.Issue;
import collabodecision.webservice.persistence.domain.Issue.IssueStatus;
import collabodecision.webservice.persistence.domain.IssueRelation;
import collabodecision.webservice.persistence.domain.IssueRelation.RelationType;
import collabodecision.webservice.persistence.domain.IssueTag;
import collabodecision.webservice.persistence.domain.Tag;
import collabodecision.webservice.service.AppUserService;
import collabodecision.webservice.service.IssueService;
import collabodecision.webservice.service.TagService;
import collabodecision.webservice.service.utils.CommentHelper;

@Service
public class IssueServiceImpl implements IssueService {

	@Autowired
	private TagDao tagDao;

	@Autowired
	private IssueDao issueDao;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private AppUserService userService;

	@Autowired
	private TagService tagService;

	@Autowired
	private CommentHelper commentHelper;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public List<ResponseWrapperIssue> getIssues(String status,
			List<String> tags, String partialTitle, boolean owned) {
		IssueStatus issueStatus = status != null ? IssueStatus.valueOf(status)
				: null;

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
				partialTitle, owned)) {

			ResponseWrapperIssue rwi = new ResponseWrapperIssue();
			rwi.setIssue(issue);

			setResponseWrapperIssueFlags(rwi);

			result.add(rwi);
		}

		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseWrapperIssue getIssue(long id, boolean withRelations) {

		Issue issue = withRelations ? issueDao.getIssueWithRelations(id)
				: issueDao.getIssue(id);
		ResponseWrapperIssue rwi = new ResponseWrapperIssue();

		rwi.setIssue(issue);
		setResponseWrapperIssueFlags(rwi);
		if (withRelations) {

			for (IssueRelation irFrom : issue.getIssueRelationsFrom()) {
				switch (irFrom.getRelationType()) {
				case DEPENDS:
					rwi.getDependsIssuesFrom().add(irFrom.getIssueTo());
					break;
				case RELATES:
					rwi.getRelatesIssues().add(irFrom.getIssueTo());
					break;
				case RESOLVES:
					rwi.getResolvesIssuesFrom().add(irFrom.getIssueTo());
					break;
				}
			}

			for (IssueRelation irTo : issue.getIssueRelationsTo()) {
				switch (irTo.getRelationType()) {
				case DEPENDS:
					rwi.getDependsIssuesTo().add(irTo.getIssueFrom());
					break;
				case RELATES:
					rwi.getRelatesIssues().add(irTo.getIssueFrom());
					break;
				case RESOLVES:
					rwi.getResolvesIssuesTo().add(irTo.getIssueFrom());
					break;
				}
			}
		}
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
		Comment comment = commentHelper.createComment(message, date);
		Issue issue = issueDao.getIssueWithRelations(id);
		comment.setIssue(issue);
		issue.getComments().add(comment);
		commentDao.saveOrUpdateComment(comment);
		issueDao.saveOrUpdateIssue(issue);
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

		boolean isUpdate = idExistingIssue != null;

		// Fetching the Issue from DB if Update ; otherwise create new
		Issue issue = isUpdate ? issueDao.getIssue(idExistingIssue)
				: new Issue();

		// No matching Issue was found
		if (isUpdate && issue == null) {
			return;
		}

		boolean isRejected = IssueStatus.valueOf(issueRequest.getIssueStatus()) == IssueStatus.REJECTED;
		// When Issue should be rejected -> must remove all Relations
		if (isRejected) {
			rejectIssue(issue);
		}

		// On Update - Delete all OneToMany Relations in advance (are added
		// again later)
		if (isUpdate) {
			clearIssueRelations(issue);
		}

		// Setting the properties of the Issue
		issue.setTitle(issueRequest.getTitle());
		issue.setDescription(issueRequest.getDescription());

		// Set Issue status
		issue.setIssueStatus(IssueStatus.valueOf(issueRequest.getIssueStatus()));
		issue.setOwner(userService.getAppUser(issueRequest.getIdOwner()));

		// Set the creator to the currently authenticated user
		AppUser creator = userService
				.getAppUserByUsername(SecurityContextHolder.getContext()
						.getAuthentication().getName());
		issue.setCreator(creator);

		// Get all the tags that are already in the DB
		List<Tag> tagsInDb = tagService.getTagsByName(issueRequest.getTags());

		for (String tagName : issueRequest.getTags()) {
			Tag newTag = new Tag(tagName);
			// Adding non existing Tags to the DB
			if (!tagsInDb.contains(newTag)) {
				tagService.addTag(newTag);
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

		// This issue depends on other issues -> only if Issue not set to
		// Rejected
		if (issueRequest.getIdsDepends() != null
				&& !issueRequest.getIdsDepends().isEmpty()) {
			List<Issue> dependingIssues = issueDao.getIssuesByIds(issueRequest
					.getIdsDepends());

			for (Issue dependingIssue : dependingIssues) {
				issue.getIssueRelationsFrom().add(
						new IssueRelation(issue, dependingIssue,
								IssueRelation.RelationType.DEPENDS));
			}

			// If the issue depends on another issue -> it is considered
			// blocked!
			// TODO: Check only blocks when DD is not accepted!
			issue.setBlocked(dependingIssues.size() > 0);
		} else {
			issue.setBlocked(false);
		}

		// This issue resolves other issues -> only if Issue not set to Rejected
		if (issueRequest.getIdsResolves() != null
				&& !issueRequest.getIdsResolves().isEmpty()) {
			List<Issue> resolvesIssues = issueDao.getIssuesByIds(issueRequest
					.getIdsResolves());
			for (Issue resolvesIssue : resolvesIssues) {
				issue.getIssueRelationsFrom().add(
						new IssueRelation(issue, resolvesIssue,
								IssueRelation.RelationType.RESOLVES));
			}
		}

		// This issue is related to other issues
		// other issues are related to this issue -> only if Issue not set to
		// Rejected
		if (issueRequest.getIdsRelates() != null
				&& !issueRequest.getIdsRelates().isEmpty()) {
			List<Issue> relatedIssues = issueDao.getIssuesByIds(issueRequest
					.getIdsRelates());
			for (Issue relatedIssue : relatedIssues) {
				issue.getIssueRelationsFrom().add(
						new IssueRelation(issue, relatedIssue,
								IssueRelation.RelationType.RELATES));
				issue.getIssueRelationsTo().add(
						new IssueRelation(relatedIssue, issue,
								IssueRelation.RelationType.RELATES));
			}
		}

		// Only needed when new (not update)
		if (!isUpdate) {
			issueDao.saveOrUpdateIssue(issue);
		}
	}

	/**
	 * Help method for rejecting an Issue properly - Must set the blocked flag
	 * of other Issues properly - Must delete all relations to other Issues
	 * 
	 * @param issue
	 *            The issue
	 */
	@Transactional(propagation=Propagation.NESTED)
	private void rejectIssue(Issue issue) {
		
		// Get all 
		Set<IssueRelation> issueRelationsTo = issue.getIssueRelationsTo();
		
		if(issueRelationsTo != null) {
			for(IssueRelation issueRelationTo : issueRelationsTo) {
				
				// 
				if(issueRelationTo.getRelationType() == RelationType.DEPENDS) {
					Issue issueDependsOn = issueRelationTo.getIssueFrom();
					
					Set<IssueRelation> dependingIssueRelations = issueDependsOn.getIssueRelationsFrom();
					
					if(dependingIssueRelations != null) {
						
						boolean hasOtherDependsRelations = false;
						
						for(IssueRelation dependingIssueRelation : dependingIssueRelations) {
							
							if(dependingIssueRelation.getRelationType() == RelationType.DEPENDS && !dependingIssueRelation.getIssueTo().equals(issue)) {
								issueDao.getIssue(issueRelationTo.getIssueFrom().getIdIssue()).setBlocked(true);
								hasOtherDependsRelations = true;
								break;
							}
						}
						
						if(!hasOtherDependsRelations) {
							issueRelationTo.getIssueFrom().setBlocked(false);
							issueDao.getIssue(issueRelationTo.getIssueFrom().getIdIssue()).setBlocked(false);
						}	
					}
				}
			}
		}
		
		clearIssueRelations(issue);
	}

	/**
	 * Help method to clear the Relations of an Issue
	 * 
	 * @param issue
	 *            The issue
	 */
	private void clearIssueRelations(Issue issue) {
		
		Set<IssueRelation> issueRelationsTo = issue.getIssueRelationsTo();
	
		if(issueRelationsTo != null) {
			for(IssueRelation issueRelationTo : issueRelationsTo) {
				
				Set<IssueRelation> issueRelationsFrom = issueRelationTo.getIssueFrom().getIssueRelationsFrom();
				
				if(issueRelationsFrom != null) {
					issueRelationsFrom.clear();
				}
			}
			
			issueRelationsTo.clear();
		}
		
		
		issue.getIssueTags().clear();
		issue.getIssueRelationsFrom().clear();
		issue.getFiles().clear();

		// Must be done - Otherwise Hibernate would result in Violation
		// Constraint!
		sessionFactory.getCurrentSession().flush();
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

		rwi.setShowInProgress(false);
		rwi.setShowRepeat(false);

		// when to show buttons for issue owner
		if (rwi.isOwner()) {
			rwi.setShowBtnRejectIssue(true);
			if (IssueStatus.NEW.equals(rwi.getIssue().getIssueStatus())
					&& !rwi.getIssue().isBlocked()
					&& rwi.getIssue().getDesignDecisions().isEmpty()) {
				rwi.setShowBtnCreateDesignDecision(true);
				rwi.setShowBtnRejectIssue(true);
			} else if (IssueStatus.RESOLVED.equals(rwi.getIssue()
					.getIssueStatus())) {
				// if issue status is resolved but should be able to be openend
				// again
				rwi.setShowBtnCreateDesignDecision(true);
				rwi.setShowBtnRejectIssue(false);
			}
		}

		if (IssueStatus.OBSOLETE.equals(rwi.getIssue().getIssueStatus())) {
			rwi.setShowObsolete(true);
		}

		if (IssueStatus.RESOLVED.equals(rwi.getIssue().getIssueStatus())) {
			rwi.setShowResolved(true);
		}
	}

	@Override
	public List<Comment> getChildComments(long idComment) {
		return commentDao.getChildComments(idComment);
	}
}
