package collabodecision.webservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.data.RequestWrapperRank;
import collabodecision.webservice.persistence.ShareDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.AlternativeRanking;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.service.AlternativeService;
import collabodecision.webservice.service.AppUserService;
import collabodecision.webservice.service.RankService;

@Service
public class RankServiceImpl implements RankService {

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private AlternativeService alternativeService;
	
	@Autowired
	private ShareDao shareDao;
	
	@Override
	@Transactional(readOnly = false)
	public void addRanks(List<RequestWrapperRank> rankings) {
		
		if(rankings == null) {
			return;
		}
		
		AppUser appUser = appUserService.getAppUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
		for(RequestWrapperRank rank : rankings) {
			
			AlternativeRanking alternativeRanking = new AlternativeRanking();
			
			Alternative alternative = alternativeService.getAlternative(rank.getIdAlternative(), false);
			
			alternativeRanking.setAlternative(alternative);
			alternativeRanking.setRank(rank.getRank());
			alternativeRanking.setShare(shareDao.getShare(appUser, alternative.getDesignDecision()));
			
			// Adding the Rankings to the alternative.
			alternative.getAlternativeRankings().add(alternativeRanking);
		}
		
	}
}
