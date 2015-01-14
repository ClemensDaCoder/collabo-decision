package collabodecision.webservice.persistence.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.AlternativeRankingDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.AlternativeRanking;
import collabodecision.webservice.persistence.domain.Share;

@Repository
public class AlternativeRankingDaoImpl extends BaseDao implements
		AlternativeRankingDao {

	@Autowired
	public AlternativeRankingDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(readOnly = false)
	public void saveOrUpdateAlternativeRanking(
			AlternativeRanking alternativeRanking) {
		getCurrentSession().saveOrUpdate(alternativeRanking);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exitsRanking(Alternative alternative, Share share) {
		Criteria crit = getCurrentSession().createCriteria(
				AlternativeRanking.class);
		crit.add(Restrictions.and(Restrictions.eq("alternative", alternative),
				Restrictions.eq("share", share)));
		return crit.list().size() == 1;
	}

}
