package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.persistence.domain.Comment;

public interface CommentService {
	void addComment(long id, String message, String date);

	List<Comment> getChildComments(long idComment);
}
