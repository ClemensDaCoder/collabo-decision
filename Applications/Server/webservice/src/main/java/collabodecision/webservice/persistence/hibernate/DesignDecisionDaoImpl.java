package collabodecision.webservice.persistence.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import collabodecision.webservice.persistence.DesignDecisionDao;
import collabodecision.webservice.persistence.domain.DesignDecision;

@Repository
public class DesignDecisionDaoImpl extends BaseDao implements DesignDecisionDao {

	@Autowired
	public DesignDecisionDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public void saveOrUpdateDesignDecision(DesignDecision designDecision) {
		getCurrentSession().saveOrUpdate(designDecision);
	}

	@Override
	public void deleteDesignDecision(long id) {
		Criteria crit = getCurrentSession().createCriteria(DesignDecision.class);
		crit.add(Restrictions.eq("idDesignDecision", id));
		getCurrentSession().delete((DesignDecision)crit.uniqueResult());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DesignDecision> getDesignDecisions() {
		return getCurrentSession().createCriteria(DesignDecision.class).list();
	}

	@Override
	public DesignDecision getDesignDecision(long id) {
		Criteria crit = getCurrentSession().createCriteria(DesignDecision.class);
		crit.add(Restrictions.eq("idDesignDecision", id));
		return (DesignDecision)crit.uniqueResult();
	}

	@Override
	public DesignDecision getDesignDecisionWithRelations(long id) {
		DesignDecision designDecision = getDesignDecision(id);
		Hibernate.initialize(designDecision.getIssue());
		Hibernate.initialize(designDecision.getAlternatives());
		Hibernate.initialize(designDecision.getComments());
		return designDecision;
	}

}
