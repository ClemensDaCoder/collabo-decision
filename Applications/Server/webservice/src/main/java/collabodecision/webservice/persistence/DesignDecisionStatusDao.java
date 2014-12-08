package collabodecision.webservice.persistence;

import collabodecision.webservice.persistence.domain.DesignDecisionStatus;
import collabodecision.webservice.persistence.domain.DesignDecisionStatus.DesignDecisionStatusValue;


public interface DesignDecisionStatusDao {

	DesignDecisionStatus getDesignDecisionStatusByName(DesignDecisionStatusValue name);

}
