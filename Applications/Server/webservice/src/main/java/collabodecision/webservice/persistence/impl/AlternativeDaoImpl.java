package collabodecision.webservice.persistence.impl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import collabodecision.webservice.persistence.AlternativeDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.Issue;

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
		// TODO Auto-generated method stub
		Alternative alternative = getAlternative(id);
		Hibernate.initialize(alternative.getComments());
		Hibernate.initialize(alternative.getAlternativeFromRelations());
		Hibernate.initialize(alternative.getAlternativeToRelations());
		Hibernate.initialize(alternative.getAlternativeRankings());
		Hibernate.initialize(alternative.getDesignDecision());
		// TODO tags ? 
		
	
		return alternative;
	}
	
}
