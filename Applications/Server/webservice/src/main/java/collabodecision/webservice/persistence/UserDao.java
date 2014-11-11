package collabodecision.webservice.persistence;

import java.util.List;

import collabodecision.webservice.persistence.domain.User;

public interface UserDao {
	User getUserByUsername(String username);
	
	User getUser(long id);
	
	List<User> getUsersNameLike(String partialName);
	
	List<User> getUsers();
}
