package collabodecision.webservice.persistence;

import java.util.List;
import java.util.Set;

import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.domain.DesignDecision.DesignDecisionStatus;

public interface DesignDecisionDao {
	
	void saveOrUpdateDesignDecision(DesignDecision designDecision);
	
	void deleteDesignDecision(long id);
	
	List<DesignDecision> getDesignDecisions();
	
	List<DesignDecision> getDesignDecisions(DesignDecisionStatus status, boolean isShareholer, boolean toRank, boolean toRate);
	
	DesignDecision getDesignDecision(long id);
	
	DesignDecision getDesignDecisionWithRelations(long id);
	
	void addComments(long id, Set<Comment> comments);
	void rateDesignDecision(long id, Integer value);
}