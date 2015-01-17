package collabodecision.webservice.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Assigns an {@link collabodecision.webservice.persistence.domain.AppUser} to a {@link collabodecision.webservice.persistence.domain.DesignDecision}, thereby granting the permission to rank the existing {@link collabodecision.webservice.persistence.domain.Alternative}
 *
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"idDesignDecision", "idUser"}))
public class Share {

	@GeneratedValue
	@Id
	private long idShare;
	
	@ManyToOne
	@JoinColumn(name="idUser", nullable=false)
	private AppUser appUser;
	
	@ManyToOne
	@JoinColumn(name="idDesignDecision", nullable=false)
	@JsonBackReference
	private DesignDecision designDecision;
	
	public Share() {
		
	}
	
	public Share(AppUser appUser, DesignDecision designDecision) {
		this.appUser = appUser;
		this.designDecision = designDecision;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser user) {
		this.appUser = user;
	}

	public DesignDecision getDesignDecision() {
		return designDecision;
	}

	public void setDesignDecision(DesignDecision designDecision) {
		this.designDecision = designDecision;
	}

	public long getIdShare() {
		return idShare;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appUser == null) ? 0 : appUser.hashCode());
		result = prime * result
				+ ((designDecision == null) ? 0 : designDecision.hashCode());
		result = prime * result + (int) (idShare ^ (idShare >>> 32));
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
		Share other = (Share) obj;
		if (appUser == null) {
			if (other.appUser != null)
				return false;
		} else if (!appUser.equals(other.appUser))
			return false;
		if (designDecision == null) {
			if (other.designDecision != null)
				return false;
		} else if (!designDecision.equals(other.designDecision))
			return false;
		if (idShare != other.idShare)
			return false;
		return true;
	}
}
