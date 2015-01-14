package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.data.RequestWrapperDesignDecision;
import collabodecision.webservice.data.ResponseWrapperDesignDecision;

public interface DesignDecisionService extends CommentService, FileService {
	List<ResponseWrapperDesignDecision> getDesignDecisions();
	
	List<ResponseWrapperDesignDecision> getDesignDecisions(String status,boolean isShareholder, boolean toRank, boolean toRate);
	
	void updateDesignDecision(long id, RequestWrapperDesignDecision designDecisionRequest);
	
	void deleteDesignDecision(long id);
	
	ResponseWrapperDesignDecision getDesignDecision(long id, boolean withRelations);
	
	void addDesignDecision(RequestWrapperDesignDecision designDecisionRequest);
	
	void rateDesignDecision(long id, Integer value);

}
