package collabodecision.webservice.persistence.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Issue {
	
	@GeneratedValue
	@Id
	private long idIssue;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable=false)
	private String description;
	
	@Column(nullable=false)
	private boolean blocked;
	
	@ManyToOne
	@JoinColumn(name="idIssueStatus", nullable=false)
	private IssueStatus issueStatus;
	
	@ManyToOne
	@JoinColumn(name="idOwner", nullable=false)
	private User owner;
	
	@ManyToOne
	@JoinColumn(name="idCreator", nullable=false)
	private User creator;
	
	@OneToMany(mappedBy="issueFrom")
	private Set<IssueRelation> issueRelationsFrom;
	
	@OneToMany(mappedBy="issueTo")
	private Set<IssueRelation> issueRelationsTo;
	
	@OneToMany(mappedBy="issue")
	private Set<DesignDecision> designDecisions;
	
	@OneToMany(mappedBy="issue")
	private Set<File> files;
	
	@OneToMany(mappedBy="issue")
	private Set<Comment> comments;
	
	@OneToMany(mappedBy="issue")
	private Set<IssueTag> issueTags;

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

	public IssueStatus getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(IssueStatus issueStatus) {
		this.issueStatus = issueStatus;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Set<IssueRelation> getIssueRelationsFrom() {
		return issueRelationsFrom;
	}

	public void setIssueRelationsFrom(Set<IssueRelation> issueRelationsFrom) {
		this.issueRelationsFrom = issueRelationsFrom;
	}

	public Set<IssueRelation> getIssueRelationsTo() {
		return issueRelationsTo;
	}

	public void setIssueRelationsTo(Set<IssueRelation> issueRelationsTo) {
		this.issueRelationsTo = issueRelationsTo;
	}

	public Set<DesignDecision> getDesignDecisions() {
		return designDecisions;
	}

	public void setDesignDecisions(Set<DesignDecision> designDecisions) {
		this.designDecisions = designDecisions;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<IssueTag> getIssueTags() {
		return issueTags;
	}

	public void setIssueTags(Set<IssueTag> issueTags) {
		this.issueTags = issueTags;
	}

	public long getIdIssue() {
		return idIssue;
	}
}
