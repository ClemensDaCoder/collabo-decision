package collabodecision.webservice.persistence.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.ShareDao;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.domain.Share;

@Repository
public class ShareDaoImpl extends BaseDao implements ShareDao {

	@Autowired
	public ShareDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	@Transactional(readOnly = true)
	public Share getShare(AppUser user, DesignDecision designDecision) {
		return (Share)getCurrentSession()
				.createCriteria(Share.class)
				.add(Restrictions.and(Restrictions.eq("appUser", user),
						Restrictions.eq("designDecision", designDecision)))
				.uniqueResult();
	}

}
