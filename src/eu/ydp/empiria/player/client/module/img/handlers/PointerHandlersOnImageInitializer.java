package eu.ydp.empiria.player.client.module.img.handlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.img.handlers.pointer.PointerDownHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.handlers.pointer.PointerMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.handlers.pointer.PointerUpHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.PointerEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer.PointerUpEvent;

public class PointerHandlersOnImageInitializer implements ITouchHandlerOnImageInitializer {

	@Inject
	PointerEventsCoordinates pointerEventsCoordinates;

	@Override
	public void addTouchMoveHandler(final TouchOnImageMoveHandler touchOnMoveHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerMoveHandlerOnImage(touchOnMoveHandler, pointerEventsCoordinates), PointerMoveEvent.getType());
	}

	@Override
	public void addTouchStartHandler(final TouchOnImageStartHandler touchStartHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerDownHandlerOnImage(touchStartHandler, pointerEventsCoordinates), PointerDownEvent.getType());
	}

	@Override
	public void addTouchEndHandler(final TouchOnImageEndHandler touchEndHandler, Widget listenOn) {
		listenOn.addDomHandler(new PointerUpHandlerOnImage(touchEndHandler, pointerEventsCoordinates), PointerUpEvent.getType());
	}
}
