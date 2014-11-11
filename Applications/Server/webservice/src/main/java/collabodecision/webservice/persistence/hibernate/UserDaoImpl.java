package collabodecision.webservice.persistence.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		Criteria crit = getCurrentSession().createCriteria(User.class);
		crit.add(Restrictions.eq("mail", username));
		return (User) crit.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	public User getUser(long id) {
		return (User) getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("idUser", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<User> getUsersNameLike(String partialName) {
		Query q = getCurrentSession().createQuery("FROM User WHERE forename || ' ' || surname like '%" + partialName + "%'");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return getCurrentSession().createCriteria(User.class).list();
	}

}
