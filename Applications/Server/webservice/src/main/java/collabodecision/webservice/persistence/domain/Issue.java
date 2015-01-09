package collabodecision.webservice.persistence.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
public class Issue {

	@GeneratedValue
	@Id
	private long idIssue;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private boolean blocked;

	@Column(nullable=false)
	private IssueStatus issueStatus;

	@ManyToOne
	@JoinColumn(name = "idOwner", nullable = false)
	private AppUser owner;

	@ManyToOne
	@JoinColumn(name = "idCreator", nullable = false)
	private AppUser creator;

	@OneToMany(mappedBy = "issueFrom", orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@JsonManagedReference
	private Set<IssueRelation> issueRelationsFrom;

	@OneToMany(mappedBy = "issueTo", orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@JsonManagedReference
	private Set<IssueRelation> issueRelationsTo;

	@OneToMany(mappedBy = "issue", orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	private Set<DesignDecision> designDecisions;

	@OneToMany(mappedBy = "issue", orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	private Set<File> files;

	@OneToMany(mappedBy = "issue", orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@JsonManagedReference
	private Set<Comment> comments;

	@OneToMany(mappedBy = "issue", orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@JsonManagedReference
	private Set<IssueTag> issueTags;


	public Issue() {
		issueRelationsFrom = new HashSet<>();
		issueRelationsTo = new HashSet<>();
		designDecisions = new HashSet<>();
		files = new HashSet<>();
		comments = new HashSet<>();
		issueTags = new HashSet<>();
	}

	public Issue(String title, String description, boolean blocked,
			IssueStatus issueStatus, AppUser owner, AppUser creator,
			Set<IssueRelation> issueRelationsFrom,
			Set<IssueRelation> issueRelationsTo,
			Set<DesignDecision> designDecisions, Set<File> files,
			Set<Comment> comments, Set<IssueTag> issueTags) {
		super();
		this.title = title;
		this.description = description;
		this.blocked = blocked;
		this.issueStatus = issueStatus;
		this.owner = owner;
		this.creator = creator;
		this.issueRelationsFrom = issueRelationsFrom;
		this.issueRelationsTo = issueRelationsTo;
		this.designDecisions = designDecisions;
		this.files = files;
		this.comments = comments;
		this.issueTags = issueTags;
	}

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

	public AppUser getOwner() {
		return owner;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	public AppUser getCreator() {
		return creator;
	}

	public void setCreator(AppUser creator) {
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
	
	public enum IssueStatus {
		NEW,
		OBSOLETE,
		REJECTED,
		RESOLVED,
		IN_PROGRESS
	}
}
