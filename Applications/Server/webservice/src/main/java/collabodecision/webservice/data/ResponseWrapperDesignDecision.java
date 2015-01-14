package collabodecision.webservice.data;

import collabodecision.webservice.persistence.domain.DesignDecision;

public class ResponseWrapperDesignDecision {

	
	private DesignDecision designDecision;
	
	private boolean isOwner;
	
	private boolean showInappropriateSolution;
	
	private boolean showStartRanking;
	
	private boolean showFinishRanking;
	
	private boolean showSelectAlternative;
	
	private boolean showDecided;
	
	private boolean showObsolete;
	
	private boolean editable;
	
	private boolean isShareholder;
	
	private boolean isRated;
	
	
	public boolean isRated() {
		return isRated;
	}

	public void setRated(boolean isRated) {
		this.isRated = isRated;
	}

	public DesignDecision getDesignDecision() {
		return designDecision;
	}

	public void setDesignDecision(DesignDecision designDecision) {
		this.designDecision = designDecision;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public boolean isShowInappropriateSolution() {
		return showInappropriateSolution;
	}

	public void setShowInappropriateSolution(boolean showInappropriateSolution) {
		this.showInappropriateSolution = showInappropriateSolution;
	}

	public boolean isShowStartRanking() {
		return showStartRanking;
	}

	public void setShowStartRanking(boolean showStartRanking) {
		this.showStartRanking = showStartRanking;
	}

	public boolean isShowFinishRanking() {
		return showFinishRanking;
	}

	public void setShowFinishRanking(boolean showFinishRanking) {
		this.showFinishRanking = showFinishRanking;
	}

	public boolean isShowSelectAlternative() {
		return showSelectAlternative;
	}

	public void setShowSelectAlternative(boolean showSelectAlternative) {
		this.showSelectAlternative = showSelectAlternative;
	}

	public boolean isShowDecided() {
		return showDecided;
	}

	public void setShowDecided(boolean booleanDecided) {
		this.showDecided = booleanDecided;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isShareholder() {
		return isShareholder;
	}

	public void setIsShareholder(boolean isShareholder) {
		this.isShareholder = isShareholder;
	}

	public boolean isShowObsolete() {
		return showObsolete;
	}

	public void setShowObsolete(boolean showObsolete) {
		this.showObsolete = showObsolete;
	}
}
