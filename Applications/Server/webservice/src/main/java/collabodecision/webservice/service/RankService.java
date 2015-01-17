package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.data.RequestWrapperRank;

/**
 * Defines the methods that are available to rank {@link collabodecision.webservice.persistence.domain.Alternative} of a
 * {@link collabodecision.webservice.persistence.domain.DesignDecision}
 *
 */
public interface RankService {

	/**
	 * Returns true if: all shareholders ranked available {@link collabodecision.webservice.persistence.domain.Alternative}
	 * 
	 * @param rankings
	 * @return boolean
	 */
	boolean addRanks(List<RequestWrapperRank> rankings);
}
