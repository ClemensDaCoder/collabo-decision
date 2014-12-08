package collabodecision.webservice.persistence.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table
public class IssueStatus {

	public enum IssueStatusValue {
		NEW,
		OBSOLETE,
		REJECTED,
		RESOLVED,
		IN_PROGRESS
	}
	
	@GeneratedValue
	@Id
	private long idIssueStatus;
	
	@Enumerated(EnumType.STRING)
	private IssueStatusValue status;
	
	@OneToMany(mappedBy="issueStatus")
	private Set<Issue> issues;

	public IssueStatusValue getStatus() {
		return status;
	}

	public void setStatus(IssueStatusValue status) {
		this.status = status;
	}

	public long getIdIssueStatus() {
		return idIssueStatus;
	}
}
