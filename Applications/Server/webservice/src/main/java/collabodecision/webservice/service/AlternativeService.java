package collabodecision.webservice.service;

import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.data.RequestWrapperAlternative;
import collabodecision.webservice.data.ResponseWrapperAlternative;

public interface AlternativeService extends CommentService, FileService{
		
	
	void deleteAlternative(long id);
	
	Alternative getAlternative(long id, boolean withRelations);
	
	ResponseWrapperAlternative getResponseWrapperAlternative(long id, boolean withRelations);
	
	void updateAlternative(long existingid, RequestWrapperAlternative alternative);

	void rankAlternative(long id, int rank);
	
	void addAlternative(RequestWrapperAlternative alternativeRequest);

}

