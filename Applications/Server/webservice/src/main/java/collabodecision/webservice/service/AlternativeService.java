package collabodecision.webservice.service;

import collabodecision.webservice.data.RequestWrapperAlternative;
import collabodecision.webservice.data.ResponseWrapperAlternative;
import collabodecision.webservice.persistence.domain.Alternative;

/**
 * Defines the methods that are available to manipulate {@link collabodecision.webservice.persistence.domain.Alternative}
 *
 */
public interface AlternativeService extends CommentService, FileService {
		
	
	/** Delets an Alternative.
	 * @param id
	 */
	void deleteAlternative(long id);
	
	/** Returns ResponseWrapperAlternative.
	 * @param id
	 * @param withRelations - true if Comments should be included.
	 * @return
	 */
	ResponseWrapperAlternative getAlternativeWrapped(long id, boolean withRelations);
	
	/** Returns Alternative.
	 * @param id
	 * @param withRelations - true if Comments should be included.
	 * @return
	 */
	Alternative getAlternative(long id, boolean withRelations);
	
	/** Updates an existing Alternative.
	 * Use when content is modified.
	 * 
	 * @param existingid
	 * @param alternative
	 */
	void updateAlternative(long existingid, RequestWrapperAlternative alternative);
	
	/** Adds an Alternative to a given {@link collabodecision.webservice.persistence.domain.DesignDecision}
	 * @param alternativeRequest
	 */
	void addAlternative(RequestWrapperAlternative alternativeRequest);

}

