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

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Represents the rating an individuals rating of a {@link collabodecision.webservice.persistence.domain.DesignDecision}
 *
 */
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
	@JsonBackReference
	private DesignDecision designDecision;
	
	@ManyToOne
	@JoinColumn(name="idRater", nullable=false)
	private Share rater;
	
	@OneToMany(mappedBy="designDecisionRating")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@JsonBackReference
	private Set<Comment> comments;

	public DesignDecisionRating() {
		comments = new HashSet<>();
	}

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

	public Share getRater() {
		return rater;
	}

	public void setRater(Share rater) {
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
