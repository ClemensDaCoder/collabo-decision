package collabodecision.webservice.persistence.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.AlternativeDao;
import collabodecision.webservice.persistence.domain.Alternative;

@Repository
public class AlternativeDaoImpl extends BaseDao implements AlternativeDao {

	@Autowired
	public AlternativeDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public Alternative getAlternative(long id) {
		// TODO Auto-generated method stub
		Criteria crit = getCurrentSession().createCriteria(Alternative.class);
		crit.add(Restrictions.eq("idAlternative", id));
		return (Alternative) crit.uniqueResult();
		
	}

	@Override
	public Alternative getAlternativeWithRelations(long id) {
		Alternative alternative = getAlternative(id);
		Hibernate.initialize(alternative.getComments());
		Hibernate.initialize(alternative.getAlternativeFromRelations());
		Hibernate.initialize(alternative.getAlternativeToRelations());
		Hibernate.initialize(alternative.getAlternativeRankings());
		Hibernate.initialize(alternative.getDesignDecision());
	
		return alternative;
	}

	@Override
	public void deleteAlternative(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdateAlternative(Alternative alternative) {
		getCurrentSession().saveOrUpdate(alternative);
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Alternative> getAlternativeByIds(Collection<Long> alternativeIds) {
		Criteria crit = getCurrentSession().createCriteria(Alternative.class);
		crit.add(Restrictions.in("idAlternative", alternativeIds));
		return crit.list();

	}
	
}
