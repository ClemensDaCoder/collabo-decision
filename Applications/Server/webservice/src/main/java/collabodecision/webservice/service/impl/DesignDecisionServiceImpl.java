package collabodecision.webservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.DesignDecisionDao;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.service.DesignDecisionService;
import collabodecision.webservice.service.utils.CommentHelper;

@Service
public class DesignDecisionServiceImpl implements DesignDecisionService {

	@Autowired
	private DesignDecisionDao designDecisionDao;

	@Autowired
	private CommentHelper commentHelper;
	
	@Autowired
	private CommentDao commentDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<DesignDecision> getDesignDecisions() {
		return designDecisionDao.getDesignDecisions();
	}

	@Override
	@Transactional(readOnly=true)
	public DesignDecision getDesignDecision(long id, boolean withRelations) {
		if(withRelations) {
			return designDecisionDao.getDesignDecisionWithRelations(id);
		}
		
		return designDecisionDao.getDesignDecision(id);
	}

	@Override
	@Transactional(readOnly=false)
	public void addDesignDecision(DesignDecision designDecision) {
		designDecisionDao.saveOrUpdateDesignDecision(designDecision);
	}

	@Override
	@Transactional(readOnly=false)
	public void updateDesignDecision(long id, DesignDecision designDecision) {
		designDecisionDao.saveOrUpdateDesignDecision(designDecision);
	}

	@Override
	@Transactional(readOnly=false)
	public void deleteDesignDecision(long id) {
		designDecisionDao.deleteDesignDecision(id);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void addComment(long id, String message, String date) {
		Comment comment = commentHelper.getComment(message, date);
		comment.setDesignDecision(designDecisionDao.getDesignDecision(id));
		commentDao.saveOrUpdateComment(comment);
	}

	@Override
	public void addFile(long id, String pathToFile) {
		// TODO Auto-generated method stub
		
	}

}
