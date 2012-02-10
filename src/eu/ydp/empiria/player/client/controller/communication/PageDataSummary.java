package eu.ydp.empiria.player.client.controller.communication;

import eu.ydp.empiria.player.client.controller.session.datasockets.SessionDataSocket;

public class PageDataSummary extends PageData {

	public PageDataSummary(String[] ts) {
		super(PageType.SUMMARY);
		titles = ts;
	}
	
	public String[] titles;
	public SessionDataSocket sessionData;

	public void setAssessmentSessionData(SessionDataSocket sdc){
		sessionData = sdc;
	}
}
