package collabodecision.webservice.persistence;

import java.util.List;

import collabodecision.webservice.persistence.domain.AppUser;

public interface UserDao {
	AppUser getUserByUsername(String username);
	
	AppUser getUser(long id);
	
	List<AppUser> getUsersNameLike(String partialName);
	
	List<AppUser> getUsers();
}
