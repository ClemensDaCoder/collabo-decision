package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.persistence.domain.Alternative;

public interface AlternativeService extends CommentService, FileService{
		
	
	void deleteAlternative(long id);
	
	Alternative getAlternative(long id, boolean withRelations);
	
	void addAlternative(Alternative alternative);
	
	void updateAlternative(long id, Alternative alternative);

	void rankAlternative(long id, long ranker, String rank);
}

