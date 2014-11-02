package collabodecision.webservice.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class RelationType {
	
	@GeneratedValue
	@Id
	private long idRelationType;
	
	@Column(nullable=false, unique=true)
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getIdRelationType() {
		return idRelationType;
	}
}
