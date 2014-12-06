package collabodecision.webservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import collabodecision.webservice.data.ResponseWrapperDesignDecision;
import collabodecision.webservice.persistence.CommentDao;
import collabodecision.webservice.persistence.DesignDecisionDao;
import collabodecision.webservice.persistence.domain.AppUser;
import collabodecision.webservice.persistence.domain.Comment;
import collabodecision.webservice.persistence.domain.DesignDecision;
import collabodecision.webservice.service.AppUserService;
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
	
	@Autowired
	private AppUserService userService;

	
	
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
	public ResponseWrapperDesignDecision getResponseWrapperDesignDesicion(
			long id, boolean withRelations) {
		DesignDecision decision;
		
		AppUser creator = userService
			     .getAppUserByUsername(SecurityContextHolder.getContext()
			       .getAuthentication().getName());
		
		if(withRelations)
		{
			decision= designDecisionDao.getDesignDecisionWithRelations(id);
		}
		else
		{
			decision = designDecisionDao.getDesignDecision(id);
		}
		
		ResponseWrapperDesignDecision response = new ResponseWrapperDesignDecision();
		response.setDesigndecision(decision);
		
		//TODO set rights .. 
		response.setBooleanDecided(true);
		response.setFinishRanking(true);
		response.setOwner(true);
		response.setShowInapropiateSolution(true);
		response.setShowSelectAlternative(true);
		response.setShowStartRanking(true);
		
		return response;
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
