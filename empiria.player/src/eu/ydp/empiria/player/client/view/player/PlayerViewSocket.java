package eu.ydp.empiria.player.client.view.player;

import eu.ydp.empiria.player.client.view.assessment.AssessmentViewSocket;

public interface PlayerViewSocket {
	
	public void setPlayerViewCarrier(PlayerViewCarrier pvd);
	
	public AssessmentViewSocket getAssessmentViewSocket(); 
}
