package eu.ydp.empiria.player.client.controller.session.datasupplier;

import eu.ydp.empiria.player.client.controller.session.SessionDataCarrier;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;

public interface AssessmentSessionDataSupplier  {
	AssessmentSessionDataSocket getAssessmentSessionDataSocket();
}
