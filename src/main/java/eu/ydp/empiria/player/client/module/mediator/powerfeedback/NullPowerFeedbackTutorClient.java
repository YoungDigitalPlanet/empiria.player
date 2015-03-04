package eu.ydp.empiria.player.client.module.mediator.powerfeedback;

import eu.ydp.empiria.player.client.module.EndHandler;

public class NullPowerFeedbackTutorClient implements PowerFeedbackTutorClient {

	@Override
	public void resetPowerFeedback() {
	}

	@Override
	public void terminatePowerFeedback() {
	}

	@Override
	public void initPowerFeedbackClient() {
	}

	@Override
	public void processUserInteraction(EndHandler endHandler) {
		endHandler.onEnd();
	}
}
