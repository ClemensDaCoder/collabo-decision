package collabodecision.webservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.data.RequestWrapperData;
import collabodecision.webservice.data.RequestWrapperDesignDecision;
import collabodecision.webservice.data.ResponseWrapperDesignDecision;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.service.DesignDecisionService;

@RestController
@RequestMapping("rest/designdecisions")
@Transactional
public class DesignDecisionController {

	@Autowired
	private DesignDecisionService designDecisionService;

	@RequestMapping(method = RequestMethod.GET)
	public List<ResponseWrapperDesignDecision> getDesignDecisions(
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "isShareholder", required = false) boolean isShareholder,
			@RequestParam(value = "torate", required = false) boolean toRate,
			@RequestParam(value = "torank", required = false) boolean toRank) {
		return designDecisionService.getDesignDecisions(status, isShareholder,
				toRank, toRate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseWrapperDesignDecision getDesignDecision(@PathVariable long id,
			@RequestParam(value = "withRelations", defaultValue = "false") boolean withRelations) {
		return designDecisionService.getDesignDecision(id, withRelations);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void addDesignDecision(@RequestBody RequestWrapperData<RequestWrapperDesignDecision> request) {
		designDecisionService.addDesignDecision(request.getData());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateDesignDecision(@PathVariable long id, @RequestBody RequestWrapperData<RequestWrapperDesignDecision> request) {
		designDecisionService.updateDesignDecision(id, request.getData());
	}
	
	@RequestMapping(value = "/{id}/solution", method = RequestMethod.POST)
	public void addSolution(@RequestParam(value="solution") long idSolutionAlternative, @PathVariable long id) {
		designDecisionService.addSolution(id, idSolutionAlternative);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteDesignDecision(@PathVariable long id, @Valid DesignDecision designDecision) {
		designDecisionService.deleteDesignDecision(id);
	}

	@RequestMapping(value = "/{idDesignDecision}/comments", method = RequestMethod.POST)
	public void addComment(@PathVariable long idDesignDecision,
			@RequestParam(value = "message") String message,
			@RequestParam(value = "date") String stringDate) {
		designDecisionService.addComment(idDesignDecision, message, stringDate);
	}

	@RequestMapping(value = "/{idDesignDecision}/rate", method = RequestMethod.POST)
	public void rateDesignDecision(@PathVariable long idDesignDecision,
			@RequestParam(value = "value") Integer value,
			@RequestParam(value = "comment", required = false) String comment,
			@RequestParam(value = "date", required = false) String stringDate) {
		designDecisionService.rateDesignDecision(idDesignDecision, value,
				comment, stringDate);
	}

}
