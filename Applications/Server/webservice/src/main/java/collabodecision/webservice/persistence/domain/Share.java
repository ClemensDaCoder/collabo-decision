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
}
