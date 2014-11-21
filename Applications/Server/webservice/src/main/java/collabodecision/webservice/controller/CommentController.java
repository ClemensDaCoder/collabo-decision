package collabodecision.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.service.CommentService;

@RestController
@RequestMapping("rest/comments")
public class CommentController {

	@Autowired
	@Qualifier("commentServiceImpl")
	private CommentService commentService;

	@RequestMapping(value = "/{idParentComment}", method = RequestMethod.POST)
	public void addComment(@PathVariable long idParentComment,
			@RequestParam(value = "message") String message,
			@RequestParam(value = "date") String stringDate) {
		commentService.addComment(idParentComment, message, stringDate);
	}
}
