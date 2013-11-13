package eu.ydp.empiria.player.client.module.tutor;

import eu.ydp.empiria.player.client.module.EndHandler;

public interface TutorEndHandler {

	void onEnd();
	
	void onEndWithDefaultAction();
	
	void setEndHandler(EndHandler endHandler);
}
