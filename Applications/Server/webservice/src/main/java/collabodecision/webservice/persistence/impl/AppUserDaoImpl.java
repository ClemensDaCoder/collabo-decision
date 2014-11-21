package collabodecision.webservice.persistence.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.AppUserDao;
import collabodecision.webservice.persistence.domain.AppUser;

@Repository
public class AppUserDaoImpl extends BaseDao implements AppUserDao {

	@Autowired
	public AppUserDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	@Transactional(readOnly = true)
	public AppUser getAppUserByUsername(String username) {
		Criteria crit = getCurrentSession().createCriteria(AppUser.class);
		crit.add(Restrictions.eq("mail", username));
		return (AppUser) crit.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	public AppUser getAppUser(long id) {
		return (AppUser) getCurrentSession().createCriteria(AppUser.class)
				.add(Restrictions.eq("idUser", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AppUser> getAppUsersNameLike(String partialName) {
		Query q = getCurrentSession().createQuery("FROM AppUser WHERE forename || ' ' || surname like '%" + partialName + "%'");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AppUser> getAppUsers() {
		return getCurrentSession().createCriteria(AppUser.class).list();
	}

	@Override
	public void saveOrUpdateAppUser(AppUser appUser) {
		getCurrentSession().saveOrUpdate(appUser);
	}

}
