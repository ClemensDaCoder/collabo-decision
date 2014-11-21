package collabodecision.webservice.persistence.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class BaseDao {
	
	private SessionFactory sessionFactory;
	
	public BaseDao (SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}
