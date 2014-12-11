package collabodecision.webservice.persistence;

import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.domain.Share;

public interface ShareDao {
	
	Share getShare(AppUser user, DesignDecision designDecision);
	
}
