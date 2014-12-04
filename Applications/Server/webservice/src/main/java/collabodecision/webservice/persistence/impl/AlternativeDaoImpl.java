package collabodecision.webservice.persistence.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import collabodecision.webservice.persistence.AlternativeDao;
import collabodecision.webservice.persistence.domain.Alternative;

@Repository
public class AlternativeDaoImpl extends BaseDao implements AlternativeDao {

	@Autowired
	public AlternativeDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public Alternative getAlternative(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alternative getAlternativeWithRelations(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
