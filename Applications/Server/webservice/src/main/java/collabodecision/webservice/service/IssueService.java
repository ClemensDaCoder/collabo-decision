package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.data.RequestWrapperIssue;
import collabodecision.webservice.data.ResponseWrapperIssue;

/**
 * Defines the methods that are available to manipulate {@link collabodecision.webservice.persistence.domain.Issue}
 *
 */
public interface IssueService extends CommentService, FileService {
	
	/** Returns a list of {@link collabodecision.webservice.persistence.domain.Issue} that correspond to the given parameters.
	 * @param status - {@link collabodecision.webservice.persistence.domain.Issue.IssueStatus}
	 * @param tags - one more {@link collabodecision.webservice.persistence.domain.Tag} assigned to the {@link collabodecision.webservice.persistence.domain.Issue}
	 * @param partialName - part of title/title of {@link collabodecision.webservice.persistence.domain.Issue}
	 * @param owned - {@link collabodecision.webservice.persistence.domain.AppUser} that owns the Issue.
	 * @return
	 */
	List<ResponseWrapperIssue> getIssues(String status, List<String> tags, String partialName, boolean owned);
	
	/** Returns an {@link collabodecision.webservice.persistence.domain.Issue}.
	 * @param id
	 * @param withRelations
	 * @return
	 */
	ResponseWrapperIssue getIssue(long id, boolean withRelations);
	
	/** Deletes an {@link collabodecision.webservice.persistence.domain.Issue}.
	 * @param id
	 */
	void deleteIssue(long id);
	
	/** Creates a new {@link collabodecision.webservice.persistence.domain.Issue}.
	 * @param issueRequest
	 */
	void addIssue(RequestWrapperIssue issueRequest);
	
	/** Updates an {@link collabodecision.webservice.persistence.domain.Issue}.
	 * @param id
	 * @param issueRequest
	 */
	void updateIssue(long id, RequestWrapperIssue issueRequest);

}
