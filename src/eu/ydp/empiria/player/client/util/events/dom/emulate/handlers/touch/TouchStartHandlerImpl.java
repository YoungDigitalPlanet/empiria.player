package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;

public class TouchStartHandlerImpl implements TouchStartHandler {

	private final TouchOnStartHandler<NativeEvent> touchStartHandler;

	public TouchStartHandlerImpl(final TouchOnStartHandler<NativeEvent> touchStartHandler) {
		this.touchStartHandler = touchStartHandler;
	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		touchStartHandler.onStart(event.getNativeEvent());
	}
}
