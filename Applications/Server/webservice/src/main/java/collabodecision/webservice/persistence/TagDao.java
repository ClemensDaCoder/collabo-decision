package collabodecision.webservice.persistence;

import java.util.Collection;
import java.util.List;

import collabodecision.webservice.persistence.domain.Tag;

public interface TagDao {
	
	List<Tag> getTagsByName(Collection<String> names);
	
	List<Tag> saveOrUpdateTags(Collection<String> tagNames);
	
}
