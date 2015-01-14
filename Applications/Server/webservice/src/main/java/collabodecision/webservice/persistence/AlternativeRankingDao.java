package collabodecision.webservice.persistence;

import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.AlternativeRanking;
import collabodecision.webservice.persistence.domain.Share;

public interface AlternativeRankingDao {
	void saveOrUpdateAlternativeRanking(AlternativeRanking alternativeRanking);
	
	boolean existsRanking(Alternative alternative, Share share);
	
}
