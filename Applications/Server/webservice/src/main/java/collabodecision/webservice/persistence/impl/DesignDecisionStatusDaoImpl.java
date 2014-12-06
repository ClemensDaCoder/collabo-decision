package collabodecision.webservice.persistence.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.DesignDecisionStatusDao;
import collabodecision.webservice.persistence.domain.DesignDecisionStatus;

@Repository
public class DesignDecisionStatusDaoImpl extends BaseDao implements DesignDecisionStatusDao{

	@Autowired
	public DesignDecisionStatusDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(readOnly = true)
	public DesignDecisionStatus getDesignDecisionStatusByName(String name) {
		return (DesignDecisionStatus) getCurrentSession()
				.createCriteria(DesignDecisionStatus.class)
				.add(Restrictions.eq("status", name.toUpperCase()))
				.uniqueResult();
	}
		
}
