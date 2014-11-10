package collabodecision.webservice.persistence;

import collabodecision.webservice.persistence.domain.User;

public interface UserDao {
	User getUserByUsername(String username);
	
	User getUser(long id);
}
