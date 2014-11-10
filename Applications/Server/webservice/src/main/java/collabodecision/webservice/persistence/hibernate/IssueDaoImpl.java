package collabodecision.webservice.persistence.hibernate;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.IssueDao;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.Issue;
import collabodecision.webservice.persistence.domain.IssueStatus;
import collabodecision.webservice.persistence.domain.Tag;

@Repository
public class IssueDaoImpl extends BaseDao implements IssueDao {

	@Autowired
	public IssueDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public void saveOrUpdateIssue(Issue issue) {
		getCurrentSession().saveOrUpdate(issue);
	}

	@Override
	public void deleteIssue(long id) {
		Criteria crit = getCurrentSession().createCriteria(Issue.class);
		crit.add(Restrictions.eq("idIssue", id));
		getCurrentSession().delete((Issue)crit.uniqueResult());
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Issue> getIssues(IssueStatus status, List<Tag> tags) {
		
		Criteria crit = getCurrentSession().createCriteria(Issue.class, "issue");
		
		// If status is set -> Only Issues with that Status
		if(status != null) {
			crit.add(Restrictions.eq("issueStatus", status));
		}
		
		// If tags are set -> Only Issues that have the same tags
		if(tags != null) {
			crit.createAlias("issue.issueTags", "issueTag");
			crit.add(Restrictions.in("issueTag.tag", tags));
			crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}
		
		return crit.list();
	}

	@Override
	public Issue getIssue(long id) {
		Criteria crit = getCurrentSession().createCriteria(Issue.class);
		crit.add(Restrictions.eq("idIssue", id));
		return (Issue)crit.uniqueResult();
	}

	@Override
	public Issue getIssueWithRelations(long id) {
		Issue issue = getIssue(id);
		Hibernate.initialize(issue.getComments());
		Hibernate.initialize(issue.getDesignDecisions());
		Hibernate.initialize(issue.getIssueRelationsFrom());
		Hibernate.initialize(issue.getIssueRelationsTo());
		Hibernate.initialize(issue.getIssueTags());
		return issue;
	}

	@Override
	public void addComments(long id, Set<Comment> comments) {
		Issue issue = getIssue(id);
		issue.setComments(comments);
		getCurrentSession().saveOrUpdate(issue);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Issue> getIssuesByIds(Collection<Long> issueIds) {
		Criteria crit = getCurrentSession().createCriteria(Issue.class);
		crit.add(Restrictions.in("idIssue", issueIds));
		return crit.list();
	}

}
