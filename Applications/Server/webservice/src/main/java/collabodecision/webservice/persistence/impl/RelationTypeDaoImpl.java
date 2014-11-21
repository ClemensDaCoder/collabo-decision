package collabodecision.webservice.persistence.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.RelationTypeDao;
import collabodecision.webservice.persistence.domain.RelationType;

@Repository
public class RelationTypeDaoImpl extends BaseDao implements RelationTypeDao {

	@Autowired
	public RelationTypeDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	@Transactional(readOnly = true)
	public RelationType getRelationTypeByType(String type) {
		return (RelationType) getCurrentSession()
				.createCriteria(RelationType.class)
				.add(Restrictions.eq("type", type)).uniqueResult();
	}

}
