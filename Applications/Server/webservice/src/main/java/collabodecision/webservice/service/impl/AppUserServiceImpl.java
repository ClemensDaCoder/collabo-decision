package collabodecision.webservice.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.AppUserDao;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.service.AppUserService;

@Service
public class AppUserServiceImpl implements UserDetailsService, AppUserService {

	@Autowired
	private AppUserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		collabodecision.webservice.persistence.domain.AppUser user = userDao.getAppUserByUsername(username);
		
		// Maybe different ROLES - according to issues maybe?
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			
		return new User(user.getMail(), user.getPassword(), true, true, true, true, authorities);
	}


	@Override
	@Transactional(readOnly=true)
	public AppUser getAppUserByUsername(String username) {
		return userDao.getAppUserByUsername(username);
	}


	@Override
	@Transactional(readOnly=true)
	public AppUser getAppUser(long id) {
		return userDao.getAppUser(id);
	}


	@Override
	@Transactional(readOnly=false)
	public void addAppUser(String email, String forename, String surname,
			String password) {
		
		AppUser appUser = new AppUser();
		appUser.setForename(forename);
		appUser.setSurname(surname);
		appUser.setMail(email);
		// Password encoding with BCrypt
		appUser.setPassword(encoder.encode(password));
		
		userDao.saveOrUpdateAppUser(appUser);
		
	}


	@Override
	@Transactional(readOnly = true)
	public List<AppUser> getAppUsers(String partialName) {
		if(partialName != null) {
			return userDao.getAppUsersNameLike(partialName);
		}
		
		return userDao.getAppUsers();
	}

}
