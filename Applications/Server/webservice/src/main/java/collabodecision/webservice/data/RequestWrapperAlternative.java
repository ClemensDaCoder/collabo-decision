package collabodecision.webservice.data;

import java.util.List;

public class RequestWrapperAlternative {

	private String description;

	private long idDesignDecision;
	
	private List<Long> idAlternativeFromsRelations;
	
	private List<Long> idAlternativesToRelations;
	
	private List<String> files;


	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getIdDesignDecision() {
		return idDesignDecision;
	}

	public void setIdDesignDecision(long designDecisionid) {
		this.idDesignDecision = designDecisionid;
	}

	public List<Long> getIdAlternativeFromsRelations() {
		return idAlternativeFromsRelations;
	}

	public void setIdAlternativeFromsRelations(
			List<Long> idalternativeFromsRelations) {
		this.idAlternativeFromsRelations = idalternativeFromsRelations;
	}

	public List<Long> getIdAlternativesToRelations() {
		return idAlternativesToRelations;
	}

	public void setIdAlternativesToRelations(List<Long> idAlternativesToRelations) {
		this.idAlternativesToRelations = idAlternativesToRelations;
	}
	
	
}
