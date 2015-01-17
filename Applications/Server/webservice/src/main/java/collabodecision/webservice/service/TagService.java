package collabodecision.webservice.service;

import java.util.Collection;
import java.util.List;

import collabodecision.webservice.persistence.domain.Tag;

/**
 * Defines the methods that are available to manipulate {@link collabodecision.webservice.persistence.domain.Tag}
 *
 */
public interface TagService {
	
	/** Returns a list of {@link collabodecision.webservice.persistence.domain.Tag} that corresponds to parameter.
	 * @param partialName
	 * @return
	 */
	List<Tag> getTags(String partialName);
	
	/** Returns a list of {@link collabodecision.webservice.persistence.domain.Tag} that corresponds to the given list of parmeters.
	 * @param names
	 * @return
	 */
	List<Tag> getTagsByName(Collection<String> names);
	
	/** Creates a new {@link collabodecision.webservice.persistence.domain.Tag}.
	 * @param tagValue
	 */
	void addTag(String tagValue);

	/** Creates a new {@link collabodecision.webservice.persistence.domain.Tag}.
	 * @param tag
	 */
	void addTag(Tag tag);
}
