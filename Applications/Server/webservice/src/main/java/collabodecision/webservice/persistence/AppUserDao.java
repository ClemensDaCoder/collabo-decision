package collabodecision.webservice.persistence;

import java.util.List;

import collabodecision.webservice.persistence.domain.AppUser;

public interface AppUserDao {
	AppUser getAppUserByUsername(String username);
	
	AppUser getAppUser(long id);
	
	List<AppUser> getAppUsersNameLike(String partialName);
	
	List<AppUser> getAppUsers();
	
	void saveOrUpdateAppUser(AppUser appUser);
}
