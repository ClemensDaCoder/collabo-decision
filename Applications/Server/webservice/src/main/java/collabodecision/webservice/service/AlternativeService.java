package collabodecision.webservice.service;

import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.data.RequestWrapperAlternative;

public interface AlternativeService extends CommentService, FileService{
		
	
	void deleteAlternative(long id);
	
	Alternative getAlternative(long id, boolean withRelations);
	
	void addAlternative(Alternative alternative);
	
	void updateAlternative(long id, Alternative alternative);

	void rankAlternative(long id, long ranker, String rank);
	
	void addRequestWrapperAlternative(RequestWrapperAlternative alternativeRequest);

}

