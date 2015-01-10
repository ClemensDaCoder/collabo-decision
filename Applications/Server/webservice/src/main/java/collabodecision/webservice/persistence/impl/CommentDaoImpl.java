package collabodecision.webservice.persistence.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.DesignDecision;

@Repository
public class CommentDaoImpl extends BaseDao implements CommentDao {

	@Autowired
	public CommentDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public void saveOrUpdateComment(Comment comment) {
		getCurrentSession().saveOrUpdate(comment);

	}

	@Override
	public void deleteComment(long id) {
		Criteria crit = getCurrentSession().createCriteria(Comment.class);
		crit.add(Restrictions.eq("idComment", id));
		getCurrentSession().delete((Comment)crit.uniqueResult());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getComments() {
		return getCurrentSession().createCriteria(Comment.class).list();
	}

	@Override
	public Comment getComment(long id) {
		Criteria crit = getCurrentSession().createCriteria(Comment.class);
		crit.add(Restrictions.eq("idComment", id));
		return (Comment)crit.uniqueResult();
	}

	@Override
	public Comment getCommentWithRelations(long id) {
		Comment comment = getComment(id);
		Hibernate.initialize(comment.getAlternative());
		Hibernate.initialize(comment.getCreator());
		Hibernate.initialize(comment.getDesignDecision());
		Hibernate.initialize(comment.getDesignDecisionRating());
		Hibernate.initialize(comment.getIssue());
		Hibernate.initialize(comment.getParentComment());
		return comment;
	}
	
	@Override
	public List<Comment> getChildComments(long commentId) {
		Criteria crit = getCurrentSession().createCriteria(Comment.class, "comment");
		
		crit.add(Restrictions.eq("parentComment", getComment(commentId)));

		List<Comment> childcomments = crit.list();
		return childcomments;
		
	}


}
