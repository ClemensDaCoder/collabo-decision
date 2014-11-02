package collabodecision.webservice.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"idIssueFrom", "idIssueTo", "idRelationType"}))
public class IssueRelation {

	@GeneratedValue
	@Id
	private long idIssueRelation;
	
	@ManyToOne
	@JoinColumn(name="idIssueFrom", nullable=false)
	private Issue issueFrom;
	
	@ManyToOne
	@JoinColumn(name="idIssueTo", nullable=false)
	private Issue issueTo;
	
	@ManyToOne
	@JoinColumn(name="idRelationType", nullable=false)
	private RelationType relationType;

	public Issue getIssueFrom() {
		return issueFrom;
	}

	public void setIssueFrom(Issue issueFrom) {
		this.issueFrom = issueFrom;
	}

	public Issue getIssueTo() {
		return issueTo;
	}

	public void setIssueTo(Issue issueTo) {
		this.issueTo = issueTo;
	}

	public RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	public long getIdIssueRelation() {
		return idIssueRelation;
	}
}
