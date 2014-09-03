package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch;

import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;

public class TouchMoveHandlerImpl implements TouchMoveHandler {

	private final TouchOnMoveHandler touchOnMoveHandler;

	public TouchMoveHandlerImpl(TouchOnMoveHandler touchOnMoveHandler) {
		this.touchOnMoveHandler = touchOnMoveHandler;
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		touchOnMoveHandler.onMove(event.getNativeEvent());
	}

}
