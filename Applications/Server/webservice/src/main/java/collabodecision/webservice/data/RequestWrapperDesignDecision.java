package collabodecision.webservice.data;

import java.util.List;

public class RequestWrapperDesignDecision {

	
	
	//private long idDesignDecision;

	
	private String title;
	private String rationale;
	private String assumption;
	private long issueid;
	private long designDecisionStatus;
	private List<String> files;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRationale() {
		return rationale;
	}
	public void setRationale(String rationale) {
		this.rationale = rationale;
	}
	public String getAssumption() {
		return assumption;
	}
	public void setAssumption(String assumption) {
		this.assumption = assumption;
	}
	public long getIssueid() {
		return issueid;
	}
	public void setIssueid(long issueid) {
		this.issueid = issueid;
	}
	public long getDesignDecisionStatus() {
		return designDecisionStatus;
	}
	public void setDesignDecisionStatus(long designDecisionStatus) {
		this.designDecisionStatus = designDecisionStatus;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}


}
