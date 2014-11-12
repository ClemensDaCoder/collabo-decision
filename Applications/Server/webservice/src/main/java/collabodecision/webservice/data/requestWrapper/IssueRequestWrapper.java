package collabodecision.webservice.data.requestWrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName("data")
public class IssueRequestWrapper {

	private String title;
	
	private String description;
	
	private boolean blocked;
	
	private long idIssueStatus;
	
	private long idOwner;
	
	private List<Long> idsDepends;
	
	private List<Long> idsResolves;
	
	private List<Long> idsRelates;
	
	private List<String> tags;
	
	private List<String> files;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public long getIdIssueStatus() {
		return idIssueStatus;
	}

	public void setIdIssueStatus(long idIssueStatus) {
		this.idIssueStatus = idIssueStatus;
	}

	public long getIdOwner() {
		return idOwner;
	}

	public void setIdOwner(long idOwner) {
		this.idOwner = idOwner;
	}

	public List<Long> getIdsDepends() {
		return idsDepends;
	}

	public void setIdsDepends(List<Long> idsDepends) {
		this.idsDepends = idsDepends;
	}

	public List<Long> getIdsResolves() {
		return idsResolves;
	}

	public void setIdsResolves(List<Long> idsResolves) {
		this.idsResolves = idsResolves;
	}

	public List<Long> getIdsRelates() {
		return idsRelates;
	}

	public void setIdsRelates(List<Long> idsRelates) {
		this.idsRelates = idsRelates;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}
	
}
