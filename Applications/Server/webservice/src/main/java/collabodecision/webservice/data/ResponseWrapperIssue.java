package collabodecision.webservice.data;

import java.util.ArrayList;
import java.util.List;

import collabodecision.webservice.persistence.domain.Issue;

public class ResponseWrapperIssue {

	private Issue issue;
	
	private boolean editable;
	private boolean showInProgress;
	private boolean showRepeat;
	private boolean showObsolete;
	private boolean showResolved;
	private boolean isOwner;
	private boolean showBtnCreateDesignDecision;
	private boolean showBtnRejectIssue;

	private List<Issue> dependsIssuesTo;
	
	private List<Issue> dependsIssuesFrom;
	
	private List<Issue> resolvesIssuesTo;
	
	private List<Issue> resolvesIssuesFrom;
	
	private List<Issue> relatesIssues;
	
	
	public ResponseWrapperIssue() {
		this.dependsIssuesTo = new ArrayList<>();
		this.dependsIssuesFrom = new ArrayList<>();
		this.resolvesIssuesTo = new ArrayList<>();
		this.resolvesIssuesFrom = new ArrayList<>();
		this.relatesIssues = new ArrayList<>();
	}
	
	
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


	public List<Issue> getDependsIssuesTo() {
		return dependsIssuesTo;
	}


	public void setDependsIssuesTo(List<Issue> dependsIssuesTo) {
		this.dependsIssuesTo = dependsIssuesTo;
	}


	public List<Issue> getDependsIssuesFrom() {
		return dependsIssuesFrom;
	}


	public void setDependsIssuesFrom(List<Issue> dependsIssuesFrom) {
		this.dependsIssuesFrom = dependsIssuesFrom;
	}


	public List<Issue> getResolvesIssuesTo() {
		return resolvesIssuesTo;
	}


	public void setResolvesIssuesTo(List<Issue> resolvesIssuesTo) {
		this.resolvesIssuesTo = resolvesIssuesTo;
	}


	public List<Issue> getResolvesIssuesFrom() {
		return resolvesIssuesFrom;
	}


	public void setResolvesIssuesFrom(List<Issue> resolvesIssuesFrom) {
		this.resolvesIssuesFrom = resolvesIssuesFrom;
	}


	public List<Issue> getRelatesIssues() {
		return relatesIssues;
	}


	public void setRelatesIssues(List<Issue> relatesIssues) {
		this.relatesIssues = relatesIssues;
	}


	public boolean isShowBtnCreateDesignDecision() {
		return showBtnCreateDesignDecision;
	}


	public void setShowBtnCreateDesignDecision(boolean showBtnCreateDesignDecision) {
		this.showBtnCreateDesignDecision = showBtnCreateDesignDecision;
	}


	public boolean isShowBtnRejectIssue() {
		return showBtnRejectIssue;
	}


	public void setShowBtnRejectIssue(boolean showBtnRejectIssue) {
		this.showBtnRejectIssue = showBtnRejectIssue;
	}
}
