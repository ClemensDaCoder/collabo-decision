package collabodecision.webservice.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.TagDao;
import collabodecision.webservice.persistence.domain.Tag;
import collabodecision.webservice.service.TagService;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagDao tagDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Tag> getTags(String partialName) {
		return tagDao.getTagsNameLike(partialName);
	}

	@Override
	public List<Tag> getTagsByName(Collection<String> names) {
		return tagDao.getTagsByName(names);
	}

	@Override
	public void addTag(Tag tag) {
		tagDao.saveOrUpdateTag(tag);
	}
	
	@Override
	public void addTag(String tagValue) {
		Tag tag = new Tag(tagValue);
		addTag(tag);
	}
}
