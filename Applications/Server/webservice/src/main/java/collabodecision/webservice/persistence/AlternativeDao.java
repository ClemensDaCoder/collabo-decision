package collabodecision.webservice.persistence;

import java.util.Collection;
import java.util.List;

import collabodecision.webservice.persistence.domain.Alternative;


public interface AlternativeDao {

	/*void saveOrUpdateDesignDecision(DesignDecision designDecision);
	
	void deleteDesignDecision(long id);
	
	List<DesignDecision> getDesignDecisions();
	
	DesignDecision getDesignDecision(long id);
	
	DesignDecision getDesignDecisionWithRelations(long id);
	*/
	Alternative getAlternative(long id);
	
	Alternative getAlternativeWithRelations(long id);
	
	void deleteAlternative(long id);
	
	void saveOrUpdateAlternative(Alternative alternative);
	
	List<Alternative> getAlternativeByIds(Collection<Long> alternativeIds);

	
}
