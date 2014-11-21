package collabodecision.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collabodecision.webservice.data.RequestWrapperData;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.service.AppUserService;

@RestController
@RequestMapping("rest/appusers")
public class AppUserController {

	@Autowired
	private AppUserService appUserService;

	@RequestMapping(method = RequestMethod.GET)
	public List<AppUser> getAppUsers(
			@RequestParam(value = "partialname", required = false) String partialName) {
		return appUserService.getAppUsers(partialName);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void addAppUser(
			@RequestBody RequestWrapperData<AppUser> appUserRequest) {
		AppUser appUser = appUserRequest.getData();
		appUserService.addAppUser(appUser.getMail(), appUser.getForename(),
				appUser.getSurname(), appUser.getPassword());
	}
}
