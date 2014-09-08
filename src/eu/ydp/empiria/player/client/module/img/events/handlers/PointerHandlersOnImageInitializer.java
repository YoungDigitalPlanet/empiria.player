package eu.ydp.empiria.player.client.module.img.events.handlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.img.events.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerDownHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerUpHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerUpEvent;

public class PointerHandlersOnImageInitializer implements ITouchHandlerOnImageInitializer {

	@Inject
	private PointerEventsCoordinates pointerEventsCoordinates;

	@Override
	public void addTouchOnImageMoveHandler(final TouchOnImageMoveHandler touchOnImageMoveHandler, Widget listenOn) {
		listenOn.addDomHandler(createTochOnImageMoveHandler(touchOnImageMoveHandler), PointerMoveEvent.getType());
	}

	private PointerMoveHandlerOnImage createTochOnImageMoveHandler(final TouchOnImageMoveHandler touchOnImageMoveHandler) {
		return new PointerMoveHandlerOnImage(touchOnImageMoveHandler, pointerEventsCoordinates);
	}

	@Override
	public void addTouchOnImageStartHandler(final TouchOnImageStartHandler touchOnImageStartHandler, Widget listenOn) {
		listenOn.addDomHandler(createTouchOnImageStartHandler(touchOnImageStartHandler), PointerDownEvent.getType());
	}

	private PointerDownHandlerOnImage createTouchOnImageStartHandler(final TouchOnImageStartHandler touchOnImageStartHandler) {
		return new PointerDownHandlerOnImage(touchOnImageStartHandler, pointerEventsCoordinates);
	}

	@Override
	public void addTouchOnImageEndHandler(final TouchOnImageEndHandler touchOnImageEndHandler, Widget listenOn) {
		listenOn.addDomHandler(createTouchOnImageEndHandler(touchOnImageEndHandler), PointerUpEvent.getType());
	}

	private PointerUpHandlerOnImage createTouchOnImageEndHandler(final TouchOnImageEndHandler touchOnImageEndHandler) {
		return new PointerUpHandlerOnImage(touchOnImageEndHandler, pointerEventsCoordinates);
	}
}
