package collabodecision.webservice.data;

import collabodecision.webservice.persistence.domain.DesignDecision;

public class ResponseWrapperDesignDecision {

	
	private DesignDecision designdecision;
	
	private boolean isOwner;
	
	private boolean showInapropiateSolution;
	
	private boolean showStartRanking;
	
	private boolean FinishRanking;
	
	private boolean ShowSelectAlternative;
	
	private boolean booleanDecided;
	
	
	public DesignDecision getDesigndecision() {
		return designdecision;
	}

	public void setDesigndecision(DesignDecision designdecision) {
		this.designdecision = designdecision;
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
		return FinishRanking;
	}

	public void setFinishRanking(boolean finishRanking) {
		FinishRanking = finishRanking;
	}

	public boolean isShowSelectAlternative() {
		return ShowSelectAlternative;
	}

	public void setShowSelectAlternative(boolean showSelectAlternative) {
		ShowSelectAlternative = showSelectAlternative;
	}

	public boolean isBooleanDecided() {
		return booleanDecided;
	}

	public void setBooleanDecided(boolean booleanDecided) {
		this.booleanDecided = booleanDecided;
	}

	
	
	
}
