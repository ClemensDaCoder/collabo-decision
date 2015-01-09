package collabodecision.webservice.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.Issue;
import collabodecision.webservice.persistence.domain.Issue.IssueStatus;
import collabodecision.webservice.persistence.domain.Tag;

public interface IssueDao {

	void saveOrUpdateIssue(Issue issue);

	void deleteIssue(long id);

	/**
	 * Gets all the Issues from the Database filtered by the parameters
	 * 
	 * @param status
	 *            All Issues with that specific status - if null all Issues
	 * @param tags
	 *            All Issues with that specific tags - if null all Issues
	 * @return List of Issues filtered by the parameters
	 */
	List<Issue> getIssues(IssueStatus status, List<Tag> tags, String partialTitle, boolean owned);

	List<Issue> getIssuesByIds(Collection<Long> issueIds);
	
	
	Issue getIssue(long id);

	Issue getIssueWithRelations(long id);

	void addComments(long id, Set<Comment> comments);
}
