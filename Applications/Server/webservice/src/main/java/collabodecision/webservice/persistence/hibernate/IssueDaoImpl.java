package collabodecision.webservice.persistence.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import collabodecision.webservice.persistence.IssueDao;
import collabodecision.webservice.persistence.domain.Issue;

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
	public void deleteIssue(Issue issue) {
		getCurrentSession().delete(issue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> getIssues() {
		return getCurrentSession().createCriteria(Issue.class).list();
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

}
