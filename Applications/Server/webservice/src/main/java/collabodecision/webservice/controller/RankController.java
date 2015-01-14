package collabodecision.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.data.RequestWrapperData;
import collabodecision.webservice.data.RequestWrapperRank;
import collabodecision.webservice.service.RankService;

@RestController
@RequestMapping("rest/rankings")
public class RankController {

	@Autowired
	private RankService rankService;
	
	@RequestMapping(method=RequestMethod.POST)
	public boolean addRanks(@RequestBody RequestWrapperData<List<RequestWrapperRank>> ranks) {
		return rankService.addRanks(ranks.getData());
	}
	
	
	
}
