package collabodecision.webservice.persistence.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.TagDao;
import collabodecision.webservice.persistence.domain.Tag;

@Repository
public class TagDaoImpl extends BaseDao implements TagDao {

	@Autowired
	public TagDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Tag> getTagsByName(Collection<String> names) {
		Criteria crit = getCurrentSession().createCriteria(Tag.class);
		crit.add(Restrictions.in("name", names));
		return crit.list();
	}

	@Override
	@Transactional(readOnly = false)
	public void saveOrUpdateTag(Tag tag) {
		getCurrentSession().saveOrUpdate(tag);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Tag> getAllTags() {
		return getCurrentSession().createCriteria(Tag.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Tag> getTagsNameLike(String partialName) {
		Query q = getCurrentSession().createQuery("FROM Tag WHERE name like '%" + partialName + "%'");
		return q.list();
	}

}
