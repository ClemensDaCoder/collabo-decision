package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.data.RequestWrapperIssue;
import collabodecision.webservice.persistence.domain.Issue;

public interface IssueService extends CommentService, FileService {
	List<Issue> getIssues(String status, List<String> tags, String partialName);
	
	Issue getIssue(long id, boolean withRelations);
	
	void deleteIssue(long id);
	
	void addIssue(RequestWrapperIssue issueRequest);
	
	void updateIssue(long id, RequestWrapperIssue issueRequest);
}
