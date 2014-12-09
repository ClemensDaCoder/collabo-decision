package collabodecision.webservice.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"idDesignDecision", "idUser"}))
public class ShareHolder {

	@GeneratedValue
	@Id
	private long idShareHolder;
	
	@ManyToOne
	@JoinColumn(name="idUser", nullable=false)
	private AppUser user;
	
	@ManyToOne
	@JoinColumn(name="idDesignDecision", nullable=false)
	private DesignDecision designDecision;
	
	public ShareHolder() {
		
	}
	
	public ShareHolder(AppUser appUser, DesignDecision designDecision) {
		this.user = appUser;
		this.designDecision = designDecision;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public DesignDecision getDesignDecision() {
		return designDecision;
	}

	public void setDesignDecision(DesignDecision designDecision) {
		this.designDecision = designDecision;
	}

	public long getIdShareHolder() {
		return idShareHolder;
	}
}
