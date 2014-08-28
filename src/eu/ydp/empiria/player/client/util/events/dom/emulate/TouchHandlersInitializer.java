package eu.ydp.empiria.player.client.util.events.dom.emulate;

import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.CreateTouchCancelHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.CreateTouchEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.CreateTouchMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.CreateTouchStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerUpEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers.PointerDownHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers.PointerMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.handlers.PointerUpHandler;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class TouchHandlersInitializer {

	@Inject
	private UserAgentUtil userAgentUtil;

	public void addTouchMoveHandler(final CreateTouchMoveHandler touchMoveHandler, Widget listenOn) {
		if (userAgentUtil.isIE()) {
			listenOn.addDomHandler(createPointerMoveHandler(touchMoveHandler), PointerMoveEvent.getType());
		} else {
			listenOn.addDomHandler(createTouchMoveHandler(touchMoveHandler), TouchMoveEvent.getType());
		}
	}

	public void addTouchStartHandler(final CreateTouchStartHandler touchStartHandler, Widget listenOn) {
		if (userAgentUtil.isIE()) {
			listenOn.addDomHandler(createPointerStartHandler(touchStartHandler), PointerDownEvent.getType());
		} else {
			listenOn.addDomHandler(createTouchStartHandler(touchStartHandler), TouchStartEvent.getType());
		}
	}

	public void addTouchEndHandler(final CreateTouchEndHandler touchEndHandler, Widget listenOn) {
		if (userAgentUtil.isIE()) {
			listenOn.addDomHandler(createPointerEndHandler(touchEndHandler), PointerUpEvent.getType());
		} else {
			listenOn.addDomHandler(createTouchEndHandler(touchEndHandler), TouchEndEvent.getType());
		}
	}

	public void addTouchCancelHandler(final CreateTouchCancelHandler touchCancelHandler, Widget listenOn) {
		listenOn.addDomHandler(createTouchCancelHandler(touchCancelHandler), TouchCancelEvent.getType());
	}

	private TouchMoveHandler createTouchMoveHandler(final CreateTouchMoveHandler touchMoveHandler) {
		return new TouchMoveHandler() {

			@Override
			public void onTouchMove(TouchMoveEvent event) {
				touchMoveHandler.onMove(event.getNativeEvent());
			}
		};
	}

	private PointerMoveHandler createPointerMoveHandler(final CreateTouchMoveHandler touchMoveHandler) {
		return new PointerMoveHandler() {

			@Override
			public void onPointerMove(PointerMoveEvent event) {
				if (event.isTouchEvent()) {
					touchMoveHandler.onMove(event.getNativeEvent());
				}

			}
		};
	}

	private TouchStartHandler createTouchStartHandler(final CreateTouchStartHandler touchStartHandler) {
		return new TouchStartHandler() {

			@Override
			public void onTouchStart(TouchStartEvent event) {
				touchStartHandler.onStart(event.getNativeEvent());
			}
		};
	}

	private PointerDownHandler createPointerStartHandler(final CreateTouchStartHandler touchStartHandler) {
		return new PointerDownHandler() {

			@Override
			public void onPointerDown(PointerDownEvent event) {
				if (event.isTouchEvent()) {
					touchStartHandler.onStart(event.getNativeEvent());
				}
			}
		};
	}

	private TouchEndHandler createTouchEndHandler(final CreateTouchEndHandler touchEndHandler) {
		return new TouchEndHandler() {

			@Override
			public void onTouchEnd(TouchEndEvent event) {
				touchEndHandler.onEnd(event.getNativeEvent());
			}
		};
	}

	private PointerUpHandler createPointerEndHandler(final CreateTouchEndHandler touchEndHandler) {
		return new PointerUpHandler() {

			@Override
			public void onPointerUp(PointerUpEvent event) {
				if (event.isTouchEvent()) {
					touchEndHandler.onEnd(event.getNativeEvent());
				}
			}
		};
	}

	private TouchCancelHandler createTouchCancelHandler(final CreateTouchCancelHandler touchCancelHandler) {
		return new TouchCancelHandler() {

			@Override
			public void onTouchCancel(TouchCancelEvent event) {
				touchCancelHandler.onCancel(event.getNativeEvent());
			}
		};
	}
}
