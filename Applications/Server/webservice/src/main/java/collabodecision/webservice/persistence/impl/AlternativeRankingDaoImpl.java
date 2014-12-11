package collabodecision.webservice.persistence.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.AlternativeRankingDao;
import collabodecision.webservice.persistence.domain.AlternativeRanking;

@Repository
public class AlternativeRankingDaoImpl extends BaseDao implements AlternativeRankingDao {

	@Autowired
	public AlternativeRankingDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(readOnly=false)
	public void saveOrUpdateAlternativeRanking(
			AlternativeRanking alternativeRanking) {
		getCurrentSession().saveOrUpdate(alternativeRanking);
	}

}
