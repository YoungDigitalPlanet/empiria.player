package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer;

import com.google.gwt.dom.client.NativeEvent;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers.PointerDownHandler;

public class PointerDownHandlerImpl implements PointerDownHandler {

	private final TouchOnStartHandler<NativeEvent> touchOnStartHandler;

	public PointerDownHandlerImpl(final TouchOnStartHandler<NativeEvent> touchOnStartHandler) {
		this.touchOnStartHandler = touchOnStartHandler;
	}

	@Override
	public void onPointerDown(PointerDownEvent event) {
		if (event.isTouchEvent()) {
			touchOnStartHandler.onStart(event.getNativeEvent());
		}
	}

}
