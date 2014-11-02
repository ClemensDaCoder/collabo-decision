package collabodecision.webservice.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
		"idAlternativeFrom", "idAlternativeTo", "idRelationType" }))
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

	@ManyToOne
	@JoinColumn(name = "idRelationType", nullable = false)
	private RelationType relationType;

}
