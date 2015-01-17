package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.persistence.domain.Comment;

/**
 * Defines the methods that are available to manipulate {@link collabodecision.webservice.persistence.domain.Comment}
 *
 */
public interface CommentService {
	void addComment(long id, String message, String date);

	/** Returns a list of {@link collabodecision.webservice.persistence.domain.Comment}
	 * @param idComment
	 * @return
	 */
	List<Comment> getChildComments(long idComment);
}
