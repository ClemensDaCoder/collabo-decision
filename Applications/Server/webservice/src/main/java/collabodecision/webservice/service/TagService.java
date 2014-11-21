package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.persistence.domain.Tag;

public interface TagService {
	List<Tag> getTags(String partialName);
}
