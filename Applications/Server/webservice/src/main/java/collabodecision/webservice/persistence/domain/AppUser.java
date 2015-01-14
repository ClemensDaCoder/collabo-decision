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
public class AppUser {
	
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
	
	@OneToMany(mappedBy="owner")
	private Set<Issue> owningIssues;
	
	@OneToMany(mappedBy="appUser")
	private Set<Share> shares;

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

	public Set<Issue> getOwningIssues() {
		return owningIssues;
	}

	public void setOwningIssues(Set<Issue> owningIssues) {
		this.owningIssues = owningIssues;
	}

	public Set<Share> getShares() {
		return shares;
	}

	public void setShares(Set<Share> shares) {
		this.shares = shares;
	}

	public long getIdUser() {
		return idUser;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idUser ^ (idUser >>> 32));
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUser other = (AppUser) obj;
		if (idUser != other.idUser)
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		return true;
	}
}
