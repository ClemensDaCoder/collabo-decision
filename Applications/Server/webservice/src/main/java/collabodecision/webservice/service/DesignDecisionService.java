package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.persistence.domain.DesignDecision;

public interface DesignDecisionService extends CommentService, FileService {
	List<DesignDecision> getDesignDecisions();
	
	DesignDecision getDesignDecision(long id, boolean withRelations);
	
	void addDesignDecision(DesignDecision designDecision);
	
	void updateDesignDecision(long id, DesignDecision designDecision);
	
	void deleteDesignDecision(long id);
}
