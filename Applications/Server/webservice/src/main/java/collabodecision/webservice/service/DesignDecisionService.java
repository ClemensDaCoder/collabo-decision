package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.data.RequestWrapperDesignDecision;
import collabodecision.webservice.data.ResponseWrapperDesignDecision;
import collabodecision.webservice.persistence.domain.DesignDecision;

public interface DesignDecisionService extends CommentService, FileService {
	List<ResponseWrapperDesignDecision> getDesignDecisions();
	
	List<ResponseWrapperDesignDecision> getDesignDecisions(String status);
	
	void addDesignDecision(DesignDecision designDecision);
	
	void updateDesignDecision(long id, DesignDecision designDecision);
	
	void deleteDesignDecision(long id);
	
	ResponseWrapperDesignDecision getDesignDecision(long id, boolean withRelations);
	
	void addRequestWrapperDesignDecision(RequestWrapperDesignDecision responseWrapperDesignDecision);
}
