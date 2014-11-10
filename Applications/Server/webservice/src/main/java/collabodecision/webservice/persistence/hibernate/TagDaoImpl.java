package collabodecision.webservice.persistence.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
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
	public List<Tag> saveOrUpdateTags(Collection<String> tagNames) {
		
		List<Tag> tags = new ArrayList<>();
		
		for (String tagName : tagNames) {
			tags.add(new Tag(tagName));
		}
		getCurrentSession().saveOrUpdate(tags);
		return tags;
	}

}
