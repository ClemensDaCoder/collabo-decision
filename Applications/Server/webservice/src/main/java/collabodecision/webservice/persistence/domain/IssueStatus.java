package collabodecision.webservice.persistence.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table
public class IssueStatus {

	@GeneratedValue
	@Id
	private long idIssueStatus;
	
	@Column(nullable=false, unique=true)
	private String status;
	
	@OneToMany(mappedBy="issueStatus")
	private Set<Issue> issues;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getIdIssueStatus() {
		return idIssueStatus;
	}
}
