package collabodecision.webservice.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class DesignDecisionStatus {
	
	@GeneratedValue
	@Id
	private long idDesignDecisionStatus;
	
	@Column(nullable=false, unique=true)
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getIdDesignDecisionStatus() {
		return idDesignDecisionStatus;
	}
}
