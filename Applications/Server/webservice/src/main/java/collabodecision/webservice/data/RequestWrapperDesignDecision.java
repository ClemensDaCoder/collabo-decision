package collabodecision.webservice.data;

import java.util.List;
import java.util.Set;

import collabodecision.webservice.persistence.domain.ShareHolder;

public class RequestWrapperDesignDecision {

	// private long idDesignDecision;

	private String title;
	private String assumption;
	private long idIssue;
	private List<String> files;
	private Set<ShareHolder> shareholders;

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

	public Set<ShareHolder> getShareholders() {
		return shareholders;
	}

	public void setShareholders(Set<ShareHolder> shareholders) {
		this.shareholders = shareholders;
	}

	public long getIdIssue() {
		return idIssue;
	}

	public void setIdIssue(long idIssue) {
		this.idIssue = idIssue;
	}

}
