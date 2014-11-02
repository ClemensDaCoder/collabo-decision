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
public class DesignDecisionRating {
	
	@GeneratedValue
	@Id
	private long idDesignDecisionRating;
	
	@Column(nullable=false)
	private int rating;
	
	@ManyToOne
	@JoinColumn(name="idDesignDecision", nullable=false)
	private DesignDecision designDecision;
	
	@ManyToOne
	@JoinColumn(name="idRater", nullable=false)
	private ShareHolder rater;
	
	@OneToMany(mappedBy="designDecisionRating")
	private Set<Comment> comments;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public DesignDecision getDesignDecision() {
		return designDecision;
	}

	public void setDesignDecision(DesignDecision designDecision) {
		this.designDecision = designDecision;
	}

	public ShareHolder getRater() {
		return rater;
	}

	public void setRater(ShareHolder rater) {
		this.rater = rater;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public long getIdDesignDecisionRating() {
		return idDesignDecisionRating;
	}
}
