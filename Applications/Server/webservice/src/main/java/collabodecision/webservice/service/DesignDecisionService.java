package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.data.RequestWrapperDesignDecision;
import collabodecision.webservice.data.ResponseWrapperDesignDecision;

/**
 * Defines the methods that are available to manipulate {@link collabodecision.webservice.persistence.domain.DesignDecision}
 *
 */
public interface DesignDecisionService extends CommentService, FileService {
	List<ResponseWrapperDesignDecision> getDesignDecisions();
	
	/** Returns a list of {@link collabodecision.webservice.persistence.domain.DesignDecision} that accords with the specified parameters.
	 * @param status - {@link collabodecision.webservice.persistence.domain.DesignDecision.DesignDecisionStatus}
	 * @param isShareholder - only design decisions where the requesting {@link collabodecision.webservice.persistence.domain.AppUser} holds a {@link collabodecision.webservice.persistence.domain.Share}
	 * @param toRank 
	 * @param toRate
	 * @return
	 */
	List<ResponseWrapperDesignDecision> getDesignDecisions(String status,boolean isShareholder, boolean toRank, boolean toRate);
	
	/** Updates a {@link collabodecision.webservice.persistence.domain.DesignDecision}
	 * @param id
	 * @param designDecisionRequest
	 */
	void updateDesignDecision(long id, RequestWrapperDesignDecision designDecisionRequest);
	
	/** Deletes a {@link collabodecision.webservice.persistence.domain.DesignDecision}
	 * @param id
	 */
	void deleteDesignDecision(long id);
	
	/** Returns a {@link collabodecision.webservice.persistence.domain.DesignDecision}
	 * @param id
	 * @param withRelations
	 * @return
	 */
	ResponseWrapperDesignDecision getDesignDecision(long id, boolean withRelations);
	
	/** Creates a new {@link collabodecision.webservice.persistence.domain.DesignDecision}
	 * @param designDecisionRequest
	 */
	void addDesignDecision(RequestWrapperDesignDecision designDecisionRequest);
	
	/** Rates the {@link collabodecision.webservice.persistence.domain.DesignDecision} (selected {@link collabodecision.webservice.persistence.domain.Alternative}).
	 * @param id
	 * @param value
	 * @param comment
	 * @param ratingTime
	 */
	void rateDesignDecision(long id, Integer value, String comment, String ratingTime);
	
	/** Updates DesignDecision by providing the id of the selected {@link collabodecision.webservice.persistence.domain.Alternative}
	 * @param idDesignDecision
	 * @param idSolutionAlternative
	 */
	void addSolution(long idDesignDecision, long idSolutionAlternative);

}
