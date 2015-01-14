package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.data.RequestWrapperRank;

public interface RankService {

	/**
	 * Returns true wenn: Alle Shareholder gerankt haben.
	 * 
	 * @param rankings
	 *            Ranking Paare: Alternative mit Rank
	 * @return true, wenn alle Shareholder gerankt haben, false sonst.
	 */
	boolean addRanks(List<RequestWrapperRank> rankings);
}
