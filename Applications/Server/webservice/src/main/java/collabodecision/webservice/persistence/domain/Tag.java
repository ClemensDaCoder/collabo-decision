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
	
	@Column
	private boolean person;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPerson() {
		return person;
	}

	public void setPerson(boolean person) {
		this.person = person;
	}

	public long getIdTag() {
		return idTag;
	}
}
