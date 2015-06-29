package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touch;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;

import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;

public class TouchStartHandlerImpl implements TouchStartHandler {

	private final TouchOnStartHandler touchStartHandler;

	public TouchStartHandlerImpl(final TouchOnStartHandler touchStartHandler) {
		this.touchStartHandler = touchStartHandler;
	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		touchStartHandler.onStart(event.getNativeEvent());
	}
}
