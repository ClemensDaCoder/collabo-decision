package collabodecision.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.data.RequestWrapperData;
import collabodecision.webservice.data.RequestWrapperIssue;
import collabodecision.webservice.persistence.domain.Issue;
import collabodecision.webservice.service.IssueService;

@RestController
@RequestMapping("rest/issues")
public class IssueController {

	@Autowired
	private IssueService issueService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Issue> getIssues(
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "tag", required = false) List<String> tags,
			@RequestParam(value = "partialtitle", required=false) String partialTitle) {
		return issueService.getIssues(status, tags, partialTitle);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Issue getIssue(
			@PathVariable long id,
			@RequestParam(value = "withRelations", defaultValue = "false") boolean withRelations) {
		return issueService.getIssue(id, withRelations);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void addIssue(
			@RequestBody RequestWrapperData<RequestWrapperIssue> request) {
		issueService.addIssue(request.getData());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateIssue(@PathVariable long id,
			@RequestBody RequestWrapperIssue issueRequest) {
		issueService.updateIssue(id, issueRequest);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteIssue(@PathVariable long id) {
		issueService.deleteIssue(id);
	}

	@RequestMapping(value = "/{idIssue}/comments", method = RequestMethod.POST)
	public void addComment(@PathVariable long idIssue,
			@RequestParam(value = "message") String message,
			@RequestParam(value = "date") String stringDate) {
		issueService.addComment(idIssue, message, stringDate);
	}
}
