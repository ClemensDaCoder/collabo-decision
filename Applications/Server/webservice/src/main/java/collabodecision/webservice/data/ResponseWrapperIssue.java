package collabodecision.webservice.data;

import collabodecision.webservice.persistence.domain.Issue;

public class ResponseWrapperIssue {

	private Issue issue;
	
	private boolean editable;
	private boolean showInProgress;
	private boolean showRepeat;
	private boolean showObsolete;
	private boolean showResolved;
	private boolean isOwner;
	
	
	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isShowInProgress() {
		return showInProgress;
	}
	public void setShowInProgress(boolean showInProgress) {
		this.showInProgress = showInProgress;
	}
	public boolean isShowRepeat() {
		return showRepeat;
	}
	public void setShowRepeat(boolean showRepeat) {
		this.showRepeat = showRepeat;
	}
	public boolean isShowObsolete() {
		return showObsolete;
	}
	public void setShowObsolete(boolean showObsolete) {
		this.showObsolete = showObsolete;
	}
	public boolean isShowResolved() {
		return showResolved;
	}
	public void setShowResolved(boolean showResolved) {
		this.showResolved = showResolved;
	}
	public boolean isOwner() {
		return isOwner;
	}
	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}
	
	
}
