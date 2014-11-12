package collabodecision.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.persistence.UserDao;
import collabodecision.webservice.persistence.domain.AppUser;

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	private UserDao userDao;
	
	@Transactional(readOnly = true)
	@RequestMapping(method=RequestMethod.GET)
	public List<AppUser> getUsers(@RequestParam(value="partialname", required=false) String partialName) {
		
		if(partialName != null) {
			return userDao.getUsersNameLike(partialName);
		}
		
		return userDao.getUsers();
	}
	
	
}
