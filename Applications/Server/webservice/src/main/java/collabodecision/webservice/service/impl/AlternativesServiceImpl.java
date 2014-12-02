package collabodecision.webservice.service.impl;

import java.util.List;

import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.service.AlternativeService;

public class AlternativesServiceImpl implements AlternativeService
{

	@Override
	public void addComment(long id, String message, String date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFile(long id, String pathToFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Alternative> getAlternatives(String status, List<String> tags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alternative getAlternative(long id, boolean withRelations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAlternative(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAlternative(Alternative alternative) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAlternative(long id, Alternative alternative) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rankAlternative(long id, long ranker, String rank) {
		// TODO Auto-generated method stub
		
	}

}
