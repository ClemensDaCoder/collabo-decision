package collabodecision.webservice.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class File {
	
	@GeneratedValue
	@Id
	private long idFile;
	
	@Column(nullable=false)
	private String pathToFile;
	
	@ManyToOne
	@JoinColumn(name="idDesignDecision")
	private DesignDecision designDecision;
	
	@ManyToOne
	@JoinColumn(name="idIssue")
	private Issue issue;
	
	@ManyToOne
	@JoinColumn(name="idAlternative")
	private Alternative alternative;

	public String getPathToFile() {
		return pathToFile;
	}

	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}

	public DesignDecision getDesignDecision() {
		return designDecision;
	}

	public void setDesignDecision(DesignDecision designDecision) {
		this.designDecision = designDecision;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Alternative getAlternative() {
		return alternative;
	}

	public void setAlternative(Alternative alternative) {
		this.alternative = alternative;
	}

	public long getIdFile() {
		return idFile;
	}
}
