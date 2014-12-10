package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.data.RequestWrapperIssue;
import collabodecision.webservice.data.ResponseWrapperIssue;

public interface IssueService extends CommentService, FileService {
	List<ResponseWrapperIssue> getIssues(String status, List<String> tags, String partialName);
	
	ResponseWrapperIssue getIssue(long id, boolean withRelations);
	
	void deleteIssue(long id);
	
	void addIssue(RequestWrapperIssue issueRequest);
	
	void updateIssue(long id, RequestWrapperIssue issueRequest);
}
