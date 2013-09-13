package eu.ydp.empiria.player.client.module.mediator.powerfeedback;

import eu.ydp.empiria.player.client.module.EndHandler;

public interface PowerFeedbackTutorClient extends PowerFeedbackClient {

	void processUserInteraction(EndHandler endHandler);
}
