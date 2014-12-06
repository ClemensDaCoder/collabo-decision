package collabodecision.webservice.persistence.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import collabodecision.webservice.persistence.DesignDecisionStatusDao;
import collabodecision.webservice.persistence.domain.DesignDecisionStatus;


public class DesignDecisionStatusDaoImpl extends BaseDao implements DesignDecisionStatusDao{

	public DesignDecisionStatusDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public DesignDecisionStatus getIssueStatusByName(String name) {
		return (DesignDecisionStatus) getCurrentSession()
				.createCriteria(DesignDecisionStatus.class)
				.add(Restrictions.eq("status", name.toUpperCase()))
				.uniqueResult();
	}
		
}
