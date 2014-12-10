package collabodecision.webservice.persistence;

import java.util.List;

import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.domain.DesignDecision.DesignDecisionStatus;

public interface DesignDecisionDao {
	
	void saveOrUpdateDesignDecision(DesignDecision designDecision);
	
	void deleteDesignDecision(long id);
	
	List<DesignDecision> getDesignDecisions();
	
	List<DesignDecision> getDesignDecisions(DesignDecisionStatus status);
	
	DesignDecision getDesignDecision(long id);
	
	DesignDecision getDesignDecisionWithRelations(long id);
	
}