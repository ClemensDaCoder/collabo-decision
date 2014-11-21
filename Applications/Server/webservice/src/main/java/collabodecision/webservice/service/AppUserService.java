package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.persistence.domain.AppUser;

public interface AppUserService {
	
	AppUser getAppUserByUsername(String username);
	
	AppUser getAppUser(long id);
	
	void addAppUser(String email, String forename, String surname, String password);
	
	List<AppUser> getAppUsers(String partialName);
	
}
