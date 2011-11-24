package eu.ydp.empiria.player.client.controller.communication;

import eu.ydp.empiria.player.client.controller.session.datasockets.SessionDataSocket;
import eu.ydp.empiria.player.client.model.feedback.AssessmentFeedbackSocket;

public class PageDataSummary extends PageData {

	public PageDataSummary(String[] ts) {
		super(PageType.SUMMARY);
		titles = ts;
	}
	
	public String[] titles;
	public SessionDataSocket sessionData;
	private AssessmentFeedbackSocket assessmentFeedbackSocket;

	public void setAssessmentSessionData(SessionDataSocket sdc){
		sessionData = sdc;
	}
	
	public void setAssessmentFeedbackSocket(AssessmentFeedbackSocket afs){
		assessmentFeedbackSocket = afs;
	}
	
	public AssessmentFeedbackSocket getAssessmentFeedbackSocket(){
		return assessmentFeedbackSocket;
	}
}
