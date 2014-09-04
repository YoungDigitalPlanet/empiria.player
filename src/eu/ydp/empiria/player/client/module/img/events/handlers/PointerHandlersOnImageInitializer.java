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
		listenOn.addDomHandler(new PointerMoveHandlerOnImage(touchOnImageMoveHandler, pointerEventsCoordinates), PointerMoveEvent.getType());
	}

	@Override
	public void addTouchOnImageStartHandler(final TouchOnImageStartHandler touchOnImageStartHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerDownHandlerOnImage(touchOnImageStartHandler, pointerEventsCoordinates), PointerDownEvent.getType());
	}

	@Override
	public void addTouchOnImageEndHandler(final TouchOnImageEndHandler touchOnImageEndHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerUpHandlerOnImage(touchOnImageEndHandler, pointerEventsCoordinates), PointerUpEvent.getType());
	}
}
