package collabodecision.webservice.persistence;

import java.util.List;
import java.util.Set;

import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.Issue;

public interface IssueDao {
	
	void saveOrUpdateIssue(Issue issue);
	
	void deleteIssue(long id);
	
	List<Issue> getIssues();
	
	Issue getIssue(long id);
	
	Issue getIssueWithRelations(long id);
	
	void addComments(long id, Set<Comment> comments);
}
