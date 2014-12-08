package collabodecision.webservice.persistence.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.IssueStatusDao;
import collabodecision.webservice.persistence.domain.IssueStatus;
import collabodecision.webservice.persistence.domain.IssueStatus.IssueStatusValue;

@Repository
public class IssueStatusDaoImpl extends BaseDao implements IssueStatusDao {

	@Autowired
	public IssueStatusDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(readOnly = true)
	public IssueStatus getIssueStatusByValue(IssueStatusValue value) {
		return (IssueStatus) getCurrentSession()
				.createCriteria(IssueStatus.class)
				.add(Restrictions.eq("status", value))
				.uniqueResult();
	}

	@Override
	public IssueStatus getIssueStatusByName(String name) {
		return getIssueStatusByValue(IssueStatusValue.valueOf(name));
	}

}
