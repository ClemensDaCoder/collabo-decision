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
public class User {
	
	@GeneratedValue
	@Id
	private long idUser;
	
	@Column(nullable=false, unique=true)
	private String mail;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String forename;
	
	@Column(nullable=false)
	private String surname;
	
	@OneToMany(mappedBy="user")
	private Set<UserSession> userSessions;
	
	@OneToMany(mappedBy="owner")
	private Set<Issue> owningIssues;
	
	@OneToMany(mappedBy="user")
	private Set<ShareHolder> shares;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Set<UserSession> getUserSessions() {
		return userSessions;
	}

	public void setUserSessions(Set<UserSession> userSessions) {
		this.userSessions = userSessions;
	}

	public Set<Issue> getOwningIssues() {
		return owningIssues;
	}

	public void setOwningIssues(Set<Issue> owningIssues) {
		this.owningIssues = owningIssues;
	}

	public Set<ShareHolder> getShares() {
		return shares;
	}

	public void setShares(Set<ShareHolder> shares) {
		this.shares = shares;
	}

	public long getIdUser() {
		return idUser;
	}
}
