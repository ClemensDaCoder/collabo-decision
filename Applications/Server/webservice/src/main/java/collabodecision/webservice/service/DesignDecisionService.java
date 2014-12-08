package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.data.RequestWrapperDesignDecision;
import collabodecision.webservice.data.ResponseWrapperDesignDecision;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.domain.Issue;

public interface DesignDecisionService extends CommentService, FileService {
	List<DesignDecision> getDesignDecisions();
	
	List<DesignDecision> getDesignDecisions(String status, List<String> tags, String partialName);
	
	DesignDecision getDesignDecision(long id, boolean withRelations);
	
	void addDesignDecision(DesignDecision designDecision);
	
	void updateDesignDecision(long id, DesignDecision designDecision);
	
	void deleteDesignDecision(long id);
	
	ResponseWrapperDesignDecision getResponseWrapperDesignDesicion(long id, boolean withRelations);
	
	void addRequestWrapperDesignDecision(RequestWrapperDesignDecision responseWrapperDesignDecision);
}
