package collabodecision.webservice.service.utils.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.service.AppUserService;
import collabodecision.webservice.service.utils.CommentHelper;

@Component
@Scope("prototype")
public class CommentHelperImpl implements CommentHelper {

	@Autowired
	private AppUserService userSerivce;
	
	
	@Override
	public Comment getComment(String message, String stringDate) {
		
		Comment comment = new Comment();
		comment.setText(message);
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
					.parse(stringDate);
			comment.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Fetch the username from the SecurityContext
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		
		comment.setCreator(userSerivce.getAppUserByUsername(username));
		return comment;
	}
	
}
