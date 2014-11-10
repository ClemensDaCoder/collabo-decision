package collabodecision.webservice.persistence;

import collabodecision.webservice.persistence.domain.IssueStatus;

public interface IssueStatusDao {
	
	IssueStatus getIssueStatusByName(String name);
	
}
