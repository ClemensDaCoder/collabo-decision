package collabodecision.webservice.persistence;

import java.util.List;

import collabodecision.webservice.persistence.domain.DesignDecision;

public interface DesignDecisionDao {
	
	void saveOrUpdateDesignDecision(DesignDecision designDecision);
	
	void deleteDesignDecision(long id);
	
	List<DesignDecision> getDesignDecisions();
	
	DesignDecision getDesignDecision(long id);
	
	DesignDecision getDesignDecisionWithRelations(long id);
	
}