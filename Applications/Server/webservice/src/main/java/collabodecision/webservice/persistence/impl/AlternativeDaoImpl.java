package collabodecision.webservice.persistence.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import collabodecision.webservice.persistence.AlternativeDao;

@Repository
public class AlternativeDaoImpl extends BaseDao implements AlternativeDao {

	@Autowired
	public AlternativeDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
}