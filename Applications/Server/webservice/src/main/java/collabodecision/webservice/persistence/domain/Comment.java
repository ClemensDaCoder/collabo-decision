package collabodecision.webservice.persistence.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Comment {

	@GeneratedValue
	@Id
	private long idComment;
	
	@Column(nullable=false)
	private String text;
	
	@Column(nullable=false)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="idParentComment")
	private Comment parentComment;
	
	@ManyToOne
	@JoinColumn(name="idIssue")
	private Issue issue;
	
	@ManyToOne
	@JoinColumn(name="idDesignDecision")
	private DesignDecision designDecision;
	
	@ManyToOne
	@JoinColumn(name="idAlternative")
	private Alternative alternative;
	
	@ManyToOne
	@JoinColumn(name="idDesignDecisionRating")
	private DesignDecisionRating designDecisionRating;
	
	@ManyToOne
	@JoinColumn(name="idCreator", nullable=false)
	private AppUser creator;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public DesignDecision getDesignDecision() {
		return designDecision;
	}

	public void setDesignDecision(DesignDecision designDecision) {
		this.designDecision = designDecision;
	}

	public Alternative getAlternative() {
		return alternative;
	}

	public void setAlternative(Alternative alternative) {
		this.alternative = alternative;
	}

	public DesignDecisionRating getDesignDecisionRating() {
		return designDecisionRating;
	}

	public void setDesignDecisionRating(DesignDecisionRating designDecisionRating) {
		this.designDecisionRating = designDecisionRating;
	}

	public AppUser getCreator() {
		return creator;
	}

	public void setCreator(AppUser creator) {
		this.creator = creator;
	}

	public long getIdComment() {
		return idComment;
	}
	
}
