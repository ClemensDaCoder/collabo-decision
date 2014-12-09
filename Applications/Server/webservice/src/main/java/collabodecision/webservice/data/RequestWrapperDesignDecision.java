package collabodecision.webservice.data;

import java.util.List;
import java.util.Set;

public class RequestWrapperDesignDecision {

	private String title;
	private String assumption;
	private long idIssue;
	private List<String> files;
	private Set<Long> appUserIds;
	private String designDecisionStatus;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAssumption() {
		return assumption;
	}

	public void setAssumption(String assumption) {
		this.assumption = assumption;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public Set<Long> getAppUserIds() {
		return appUserIds;
	}

	public void setAppUserIds(Set<Long> shareholders) {
		this.appUserIds = shareholders;
	}

	public long getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(long idIssue) {
		this.idIssue = idIssue;
	}

	public String getDesignDecisionStatus() {
		return designDecisionStatus;
	}

	public void setDesignDecisionStatus(String designDecisionStatus) {
		this.designDecisionStatus = designDecisionStatus;
	}

}
