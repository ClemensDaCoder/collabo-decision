package collabodecision.webservice.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Represents an individual rank of a Shareholder on an {@link collabodecision.webservice.persistence.domain.Alternative}.
 * 
 *  @see collabodecision.webservice.persistence.domain.Share
 *
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "idAlternative",
		"idShare", "rank" }))
public class AlternativeRanking {

	@GeneratedValue
	@Id
	private long idAlternativeRanking;

	@Column(nullable = false)
	private int rank;

	@ManyToOne
	@JoinColumn(name = "idAlternative", nullable = false)
	@JsonBackReference
	private Alternative alternative;

	@ManyToOne
	@JoinColumn(name = "idShare", nullable = false)
	private Share share;

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Alternative getAlternative() {
		return alternative;
	}

	public void setAlternative(Alternative alternative) {
		this.alternative = alternative;
	}

	public Share getShare() {
		return share;
	}

	public void setShare(Share share) {
		this.share = share;
	}

	public long getIdAlternativeRanking() {
		return idAlternativeRanking;
	}
}
