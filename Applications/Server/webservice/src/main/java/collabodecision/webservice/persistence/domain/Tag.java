package collabodecision.webservice.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Tag {

	@GeneratedValue
	@Id
	private long idTag;
	
	@Column(nullable=false, unique=true)
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getIdTag() {
		return idTag;
	}
}
