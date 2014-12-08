package collabodecision.webservice.data;

import collabodecision.webservice.persistence.domain.Alternative;

public class ResponseWrapperAlternative {

	Alternative alternative;
	
	boolean editable;

	public Alternative getAlternative() {
		return alternative;
	}

	public void setAlternative(Alternative alternative) {
		this.alternative = alternative;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	
}
