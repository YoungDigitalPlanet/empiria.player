package eu.ydp.empiria.player.client.util.events.dom.emulate;

import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerDownHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerMoveHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.pointer.PointerUpHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch.TouchCancelHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch.TouchEndHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch.TouchMoveHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touch.TouchStartHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer.events.PointerUpEvent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class TouchHandlersInitializer {

	@Inject
	private UserAgentUtil userAgentUtil;

	public void addTouchMoveHandler(final TouchOnMoveHandler touchOnMoveHandler, Widget listenOn) {
		if (userAgentUtil.isIE()) {
			listenOn.addDomHandler(new PointerMoveHandlerImpl(touchOnMoveHandler), PointerMoveEvent.getType());
		} else {
			listenOn.addDomHandler(new TouchMoveHandlerImpl(touchOnMoveHandler), TouchMoveEvent.getType());
		}
	}

	public void addTouchStartHandler(final TouchOnStartHandler touchStartHandler, Widget listenOn) {
		if (userAgentUtil.isIE()) {
			listenOn.addDomHandler(new PointerDownHandlerImpl(touchStartHandler), PointerDownEvent.getType());
		} else {
			listenOn.addDomHandler(new TouchStartHandlerImpl(touchStartHandler), TouchStartEvent.getType());
		}
	}

	public void addTouchEndHandler(final TouchOnEndHandler touchEndHandler, Widget listenOn) {
		if (userAgentUtil.isIE()) {
			listenOn.addDomHandler(new PointerUpHandlerImpl(touchEndHandler), PointerUpEvent.getType());
		} else {
			listenOn.addDomHandler(new TouchEndHandlerImpl(touchEndHandler), TouchEndEvent.getType());
		}
	}

	public void addTouchCancelHandler(final TouchOnCancelHandler touchCancelHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchCancelHandlerImpl(touchCancelHandler), TouchCancelEvent.getType());
	}
}
