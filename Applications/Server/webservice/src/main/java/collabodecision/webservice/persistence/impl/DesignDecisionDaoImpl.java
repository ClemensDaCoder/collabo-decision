package collabodecision.webservice.persistence.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import collabodecision.webservice.persistence.AppUserDao;
import collabodecision.webservice.persistence.DesignDecisionDao;
import collabodecision.webservice.persistence.DesignDecisionRatingDao;
import collabodecision.webservice.persistence.ShareDao;
import collabodecision.webservice.persistence.domain.Alternative;
import collabodecision.webservice.persistence.domain.AlternativeRanking;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.persistence.domain.DesignDecisionRating;
import collabodecision.webservice.persistence.domain.Share;
import collabodecision.webservice.persistence.domain.DesignDecision.DesignDecisionStatus;
import collabodecision.webservice.persistence.domain.Issue;
import collabodecision.webservice.service.AppUserService;

@Repository
public class DesignDecisionDaoImpl extends BaseDao implements DesignDecisionDao {

	@Autowired
	private AppUserDao userDao;


	@Autowired
	public DesignDecisionDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public void saveOrUpdateDesignDecision(DesignDecision designDecision) {
		getCurrentSession().saveOrUpdate(designDecision);
	}

	@Override
	public void deleteDesignDecision(long id) {
		Criteria crit = getCurrentSession().createCriteria(DesignDecision.class);
		crit.add(Restrictions.eq("idDesignDecision", id));
		getCurrentSession().delete((DesignDecision)crit.uniqueResult());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DesignDecision> getDesignDecisions() {
		return getCurrentSession().createCriteria(DesignDecision.class).list();
	}

	@Override
	public DesignDecision getDesignDecision(long id) {
		Criteria crit = getCurrentSession().createCriteria(DesignDecision.class);
		crit.add(Restrictions.eq("idDesignDecision", id));
		return (DesignDecision)crit.uniqueResult();
	}

	@Override
	public DesignDecision getDesignDecisionWithRelations(long id) {
		DesignDecision designDecision = getDesignDecision(id);
		//Hibernate.initialize(designDecision.getIssue());
		Hibernate.initialize(designDecision.getAlternatives());
		Hibernate.initialize(designDecision.getComments());
		Hibernate.initialize(designDecision.getDesignDecisionRatings());
		return designDecision;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DesignDecision> getDesignDecisions(DesignDecisionStatus status,boolean isShareholder, boolean toRank, boolean toRate) {
		Criteria crit = getCurrentSession().createCriteria(DesignDecision.class, "designDecision");

		// If status is set -> Only Issues with that Status
		if (status != null) {
			crit.add(Restrictions.eq("designDecisionStatus", status));
		}
		
		//Only issues were user is Shareholder
		if(isShareholder)
		{
			String username = SecurityContextHolder.getContext()
					.getAuthentication().getName();
			AppUser user = userDao.getAppUserByUsername(username);
			
			crit.createAlias("designDecision.shares", "share");
			crit.add(Restrictions.eq("share.appUser", user));
		
		}
		
		if(toRank)
		{
			crit.add(Restrictions.eq("designDecisionStatus", DesignDecisionStatus.RANK_ALTERNATIVES));
		}
		if(toRate)
		{
			crit.add(Restrictions.eq("designDecisionStatus", DesignDecisionStatus.DECIDED));
		}
		List<DesignDecision> designDecisions = crit.list();
		return designDecisions;
	}

	@Override
	public void addComments(long id, Set<Comment> comments) {
		// TODO Auto-generated method stub
		DesignDecision decision = getDesignDecision(id);
		decision.setComments(comments);
		getCurrentSession().saveOrUpdate(decision);
	}


}
