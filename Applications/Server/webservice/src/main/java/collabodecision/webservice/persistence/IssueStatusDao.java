package collabodecision.webservice.persistence;

import collabodecision.webservice.persistence.domain.IssueStatus;
import collabodecision.webservice.persistence.domain.IssueStatus.IssueStatusValue;

public interface IssueStatusDao {
	
	IssueStatus getIssueStatusByValue(IssueStatusValue value);
	
	IssueStatus getIssueStatusByName(String name);
	
}
