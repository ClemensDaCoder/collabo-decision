package collabodecision.webservice.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"idTag", "idIssue"}))
public class IssueTag {

	@GeneratedValue
	@Id
	private long idIssueTag;
	
	@ManyToOne
	@JoinColumn(name="idTag", nullable=false)
	private Tag tag;
	
	@ManyToOne
	@JoinColumn(name="idIssue", nullable=false)
	@JsonBackReference
	private Issue issue;

	public IssueTag() {
		
	}
	
	public IssueTag(Tag tag, Issue issue) {
		this.tag = tag;
		this.issue = issue;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public long getIdIssueTag() {
		return idIssueTag;
	}
}
