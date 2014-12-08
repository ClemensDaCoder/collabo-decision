package collabodecision.webservice.persistence.domain;

import java.sql.Date;
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

@Entity
@Table
public class DesignDecision {

	@Id
	@GeneratedValue
	private long idDesignDecision;

	@Column(nullable = false)
	private String title;

	@Column(nullable = true)
	private String rationale;

	@Column(nullable = false)
	private String assumption;
	
	@Column(nullable = false)
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "idIssue", nullable = false)
	private Issue issue;

	@ManyToOne
	@JoinColumn(name = "idDesignDecisionStatus", nullable = false)
	private DesignDecisionStatus designDecisionStatus;
	
	@OneToMany(mappedBy = "designDecision")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<DesignDecisionRating> designDecisionRatings;

	@OneToMany(mappedBy = "designDecision")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<Alternative> alternatives;

	@OneToMany(mappedBy = "designDecision")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<Comment> comments;

	@OneToMany(mappedBy = "designDecision")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<File> files;

	@OneToMany(mappedBy = "designDecision")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<ShareHolder> shareHolders;

	public Set<ShareHolder> getShareHolders() {
		return shareHolders;
	}

	public void setShareHolders(Set<ShareHolder> shareHolders) {
		this.shareHolders = shareHolders;
	}

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

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public DesignDecisionStatus getDesignDecisionStatus() {
		return designDecisionStatus;
	}

	public void setDesignDecisionStatus(
			DesignDecisionStatus designDecisionStatus) {
		this.designDecisionStatus = designDecisionStatus;
	}

	public Set<Alternative> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(Set<Alternative> alternatives) {
		this.alternatives = alternatives;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public long getIdDesignDecision() {
		return idDesignDecision;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
