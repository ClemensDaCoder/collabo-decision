package collabodecision.webservice.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "idAlternative",
		"idRanker" }))
public class AlternativeRanking {

	@GeneratedValue
	@Id
	private long idAlternativeRanking;

	@Column(nullable = false)
	private int rank;

	@ManyToOne
	@JoinColumn(name = "idAlternative", nullable = false)
	private Alternative alternative;

	@ManyToOne
	@JoinColumn(name = "idRanker", nullable = false)
	private Share ranker;

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

	public Share getRanker() {
		return ranker;
	}

	public void setRanker(Share ranker) {
		this.ranker = ranker;
	}

	public long getIdAlternativeRanking() {
		return idAlternativeRanking;
	}
}
