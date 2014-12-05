package collabodecision.webservice.persistence;

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
	
}
