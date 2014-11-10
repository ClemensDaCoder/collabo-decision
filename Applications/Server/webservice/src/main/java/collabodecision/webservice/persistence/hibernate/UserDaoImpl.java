package collabodecision.webservice.persistence.hibernate;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.UserDao;
import collabodecision.webservice.persistence.domain.User;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	@Transactional(readOnly=true)
	public User getUserByUsername(String username) {
		Criteria crit = getCurrentSession().createCriteria(User.class);
		crit.add(Restrictions.eq("mail", username));
		return (User) crit.uniqueResult();
	}

}
