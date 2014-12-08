package collabodecision.webservice.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class DesignDecisionStatus {
		
	public enum DesignDecisionStatusValue {
		COLLECTING_ALTERNATIVES,
		RANK_ALTERNATIVES,
		SELECTING_ALTERNATIVES,
		DECIDED,
		OBSOLETE,
		INAPPROPRIATE_SOLUTION,
		BLOCKED
	}
	
	
	@GeneratedValue
	@Id
	private long idDesignDecisionStatus;
	
	@Enumerated(EnumType.STRING)
	private DesignDecisionStatusValue status;
	

	public DesignDecisionStatusValue getStatus() {
		return status;
	}

	public void setStatus(DesignDecisionStatusValue status) {
		this.status = status;
	}

	public long getIdDesignDecisionStatus() {
		return idDesignDecisionStatus;
	}
}
