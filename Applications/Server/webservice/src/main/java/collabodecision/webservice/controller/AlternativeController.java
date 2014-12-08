package collabodecision.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.data.RequestWrapperAlternative;
import collabodecision.webservice.data.RequestWrapperData;
import collabodecision.webservice.data.ResponseWrapperAlternative;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.service.AlternativeService;

@RestController
@RequestMapping("rest/alternatives")
public class AlternativeController {

	
	@Autowired
	private AlternativeService alternativeService;

	@RequestMapping(method = RequestMethod.POST)
	public void addAlternative(@RequestBody RequestWrapperData<RequestWrapperAlternative> request) {
		alternativeService.addRequestWrapperAlternative(request.getData());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateAlternative(@PathVariable long id,
			@RequestBody RequestWrapperData<Alternative> request) {
		alternativeService.updateAlternative(id, request.getData());
	}
	
	@RequestMapping(value = "/{idAlternative}/comments", method = RequestMethod.POST)
	public void addComment(@PathVariable long idAlternative,
			@RequestParam(value = "message") String message,
			@RequestParam(value = "date") String stringDate) {
		alternativeService.addComment(idAlternative, message, stringDate);
	}
	
	@RequestMapping(value = "/{idAlternative}/rankings", method = RequestMethod.POST)
	public void rankAlternative(@PathVariable long idAlternative,
			@RequestParam(value = "idRanker") long idRanker,
			// #TODO: ranking
			@RequestParam(value = "rank") String stringRank) {
		alternativeService.rankAlternative(idAlternative, idRanker, stringRank);
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseWrapperAlternative getAlternative(
			@PathVariable long id,
			@RequestParam(value = "withRelations", defaultValue = "false") boolean withRelations) {
		return alternativeService.getResponseWrapperAlternative(id, withRelations);
	}
	
	
	
}
