package collabodecision.webservice.service.utils;

import collabodecision.webservice.persistence.domain.Comment;

public interface CommentHelper {
	Comment createComment(String message, String stringDate);
}
