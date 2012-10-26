package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlers;

public interface TouchRecognitionFactory {
	/**
	 * @param listenOn
	 * @param emulateClickAsTouch czy emulowac operacje myszka jako touche
	 * @return
	 */
	public HasTouchHandlers getTouchRecognition(Widget listenOn, Boolean emulateClickAsTouch);
}
