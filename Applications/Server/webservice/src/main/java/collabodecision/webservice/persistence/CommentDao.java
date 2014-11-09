package collabodecision.webservice.persistence;

import java.util.List;

import collabodecision.webservice.persistence.domain.Comment;

public interface CommentDao {
	
	void saveOrUpdateComment(Comment comment);
	
	void deleteComment(long id);
	
	List<Comment> getComments();
	
	Comment getComment(long id);
	
	Comment getCommentWithRelations(long id);

}
