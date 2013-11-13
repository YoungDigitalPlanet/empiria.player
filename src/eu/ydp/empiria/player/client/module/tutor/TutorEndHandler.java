package eu.ydp.empiria.player.client.module.tutor;

import eu.ydp.empiria.player.client.module.EndHandler;

public interface TutorEndHandler {

	void onEnd(boolean shouldExecuteDefaultAction);
	
	void setEndHandler(EndHandler endHandler);
}
