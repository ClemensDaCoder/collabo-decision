package collabodecision.webservice.data;

import java.util.List;

public class RequestWrapperAlternative {

	

	private String description;

	private long designDecisionid;
	
	private long idOwner;
	
	private List<Long> idalternativeFromsRelations;
	
	private List<Long> idalternativesToRelations;
	
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

	public long getDesignDecisionid() {
		return designDecisionid;
	}

	public void setDesignDecisionid(long designDecisionid) {
		this.designDecisionid = designDecisionid;
	}

	public long getIdOwner() {
		return idOwner;
	}

	public void setIdOwner(long idOwner) {
		this.idOwner = idOwner;
	}

	public List<Long> getIdalternativeFromsRelations() {
		return idalternativeFromsRelations;
	}

	public void setIdalternativeFromsRelations(
			List<Long> idalternativeFromsRelations) {
		this.idalternativeFromsRelations = idalternativeFromsRelations;
	}

	public List<Long> getIdalternativesToRelations() {
		return idalternativesToRelations;
	}

	public void setIdalternativesToRelations(List<Long> idalternativesToRelations) {
		this.idalternativesToRelations = idalternativesToRelations;
	}
	
	
}
