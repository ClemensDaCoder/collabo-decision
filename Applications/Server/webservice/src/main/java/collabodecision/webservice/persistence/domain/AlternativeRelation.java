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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
		"idAlternativeFrom", "idAlternativeTo", "relationType" }))
public class AlternativeRelation {

	@GeneratedValue
	@Id
	private long idAlternativeRelation;

	@ManyToOne
	@JoinColumn(name = "idAlternativeFrom", nullable = false)
	private Alternative alternativeFrom;

	@ManyToOne
	@JoinColumn(name = "idAlternativeTo", nullable = false)
	private Alternative alternativeTo;

	@Column(nullable=false)
	private RelationType relationType;
	
	
	public Alternative getAlternativeFrom() {
		return alternativeFrom;
	}




	public void setAlternativeFrom(Alternative alternativeFrom) {
		this.alternativeFrom = alternativeFrom;
	}




	public Alternative getAlternativeTo() {
		return alternativeTo;
	}




	public void setAlternativeTo(Alternative alternativeTo) {
		this.alternativeTo = alternativeTo;
	}




	public RelationType getRelationType() {
		return relationType;
	}




	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}




	public long getIdAlternativeRelation() {
		return idAlternativeRelation;
	}


	public enum RelationType {
		
	}

}
