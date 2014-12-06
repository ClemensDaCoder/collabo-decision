package collabodecision.webservice.data;

import collabodecision.webservice.persistence.domain.DesignDecision;

public class ResponseWrapperDesignDecision {

	
	private DesignDecision designDecision;
	
	private boolean isOwner;
	
	private boolean showInapropiateSolution;
	
	private boolean showStartRanking;
	
	private boolean finishRanking;
	
	private boolean showSelectAlternative;
	
	private boolean booleanDecided;
	
	
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

	public boolean isShowInapropiateSolution() {
		return showInapropiateSolution;
	}

	public void setShowInapropiateSolution(boolean showInapropiateSolution) {
		this.showInapropiateSolution = showInapropiateSolution;
	}

	public boolean isShowStartRanking() {
		return showStartRanking;
	}

	public void setShowStartRanking(boolean showStartRanking) {
		this.showStartRanking = showStartRanking;
	}

	public boolean isFinishRanking() {
		return finishRanking;
	}

	public void setFinishRanking(boolean finishRanking) {
		finishRanking = finishRanking;
	}

	public boolean isShowSelectAlternative() {
		return showSelectAlternative;
	}

	public void setShowSelectAlternative(boolean showSelectAlternative) {
		showSelectAlternative = showSelectAlternative;
	}

	public boolean isBooleanDecided() {
		return booleanDecided;
	}

	public void setBooleanDecided(boolean booleanDecided) {
		this.booleanDecided = booleanDecided;
	}

	
	
	
}
