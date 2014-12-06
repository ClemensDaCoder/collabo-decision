package collabodecision.webservice.persistence;

import collabodecision.webservice.persistence.domain.DesignDecisionStatus;


public interface DesignDecisionStatusDao {

	DesignDecisionStatus getIssueStatusByName(String name);

}
