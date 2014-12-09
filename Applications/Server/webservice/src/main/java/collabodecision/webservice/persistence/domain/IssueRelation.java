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

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"idIssueFrom", "idIssueTo", "relationType"}))
public class IssueRelation {

	@GeneratedValue
	@Id
	private long idIssueRelation;
	
	@ManyToOne
	@JoinColumn(name="idIssueFrom", nullable=false)
	@JsonBackReference
	private Issue issueFrom;
	
	@ManyToOne
	@JoinColumn(name="idIssueTo", nullable=false)
	@JsonBackReference
	private Issue issueTo;
	
	@Column(nullable=false)
	private RelationType relationType;
	
	public IssueRelation() {
		
	}
	
	public IssueRelation(Issue issueFrom, Issue issueTo,
			RelationType relationType) {
		this.issueFrom = issueFrom;
		this.issueTo = issueTo;
		this.relationType = relationType;
	}

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
	
	public enum RelationType {
		DEPENDS,
		RELATES,
		RESOLVES
	}
}
