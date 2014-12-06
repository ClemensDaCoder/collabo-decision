package collabodecision.webservice.persistence;

import collabodecision.webservice.persistence.domain.DesignDecisionStatus;


public interface DesignDecisionStatusDao {

	DesignDecisionStatus getDesignDecisionStatusByName(String name);

}
