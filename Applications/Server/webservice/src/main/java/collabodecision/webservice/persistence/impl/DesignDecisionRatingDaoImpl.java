package collabodecision.webservice.persistence.impl;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.DesignDecisionRatingDao;
import collabodecision.webservice.persistence.domain.DesignDecisionRating;

public class DesignDecisionRatingDaoImpl extends BaseDao implements DesignDecisionRatingDao{

	public DesignDecisionRatingDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(readOnly=false)
	public void saveOrUpdateDesignDecisionRating(
			DesignDecisionRating designDecisionRating) {
		// TODO Auto-generated method stub
		getCurrentSession().saveOrUpdate(designDecisionRating);
		
	}

}
