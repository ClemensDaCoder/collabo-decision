package collabodecision.webservice.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.data.RequestWrapperRank;
import collabodecision.webservice.data.ResponseWrapperDesignDecision;
import collabodecision.webservice.persistence.AlternativeRankingDao;
import collabodecision.webservice.persistence.ShareDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.AlternativeRanking;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.domain.Share;
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
	
	@Autowired
	private AlternativeRankingDao alternativeRankingDao;

	@Override
	@Transactional(readOnly = false)
	public boolean addRanks(List<RequestWrapperRank> rankings) {

		if (rankings == null) {
			return false;
		}

		AppUser appUser = appUserService
				.getAppUserByUsername(SecurityContextHolder.getContext()
						.getAuthentication().getName());

		DesignDecision designDecision = null;
		
		for (RequestWrapperRank rank : rankings) {

			AlternativeRanking alternativeRanking = new AlternativeRanking();

			Alternative alternative = alternativeService.getAlternative(
					rank.getIdAlternative(), false);
			
			designDecision = alternative.getDesignDecision();

			alternativeRanking.setAlternative(alternative);
			alternativeRanking.setRank(rank.getRank());
			alternativeRanking.setShare(shareDao.getShare(appUser,
					designDecision));

			// Adding the Rankings to the alternative.
			alternative.getAlternativeRankings().add(alternativeRanking);
		}
		
		// Check if all Shareholders have ranked
		Set<Alternative> alternatives = designDecision.getAlternatives();
		Set<Share> shares = designDecision.getShares();
		
		// Find out, if all shareholders of the DD have ranked all the Alternatives of the DD
		for(Alternative alternative : alternatives) {
			for(Share share : shares) {
				
				if(!alternativeRankingDao.exitsRanking(alternative, share)) {
					return false;
				}		
			}
		}

		// DesignDecision ranked completely => change to Selecting Alternatives
		designDecision.setDesignDecisionStatus(DesignDecision.DesignDecisionStatus.SELECTING_ALTERNATIVES);
		return true;
	}
}
