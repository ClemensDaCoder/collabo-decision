package collabodecision.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.persistence.TagDao;
import collabodecision.webservice.persistence.domain.Tag;

@RestController
@RequestMapping("api/tags")
public class TagController {

	@Autowired
	private TagDao tagDao;
	
	@RequestMapping(method=RequestMethod.GET)
	@Transactional(readOnly=true)
	public List<Tag> getTags(@RequestParam(value="partialname") String partialName) {
		return tagDao.getTagsNameLike(partialName);
	}
}
