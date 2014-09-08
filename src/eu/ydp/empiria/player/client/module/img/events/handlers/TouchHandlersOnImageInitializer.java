package eu.ydp.empiria.player.client.module.img.events.handlers;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchEndHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchStartHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;

public class TouchHandlersOnImageInitializer implements ITouchHandlerOnImageInitializer {

	@Inject
	private TouchToImageEvent touchEventsCoordinates;

	@Override
	public void addTouchOnImageMoveHandler(final TouchOnImageMoveHandler touchOnMoveHandler, Widget listenOn) {
		listenOn.addDomHandler(createTouchOnImageMoveHandler(touchOnMoveHandler), TouchMoveEvent.getType());
	}

	private TouchMoveHandlerOnImage createTouchOnImageMoveHandler(final TouchOnImageMoveHandler touchOnMoveHandler) {
		return new TouchMoveHandlerOnImage(touchOnMoveHandler, touchEventsCoordinates);
	}

	@Override
	public void addTouchOnImageStartHandler(final TouchOnImageStartHandler touchStartHandler, Widget listenOn) {
		listenOn.addDomHandler(createTouchOnImageStartHandler(touchStartHandler), TouchStartEvent.getType());

	}

	private TouchStartHandlerOnImage createTouchOnImageStartHandler(final TouchOnImageStartHandler touchStartHandler) {
		return new TouchStartHandlerOnImage(touchStartHandler, touchEventsCoordinates);
	}

	@Override
	public void addTouchOnImageEndHandler(final TouchOnImageEndHandler touchEndHandler, Widget listenOn) {
		listenOn.addDomHandler(createTouchOnImageEndHandler(touchEndHandler), TouchEndEvent.getType());

	}

	private TouchEndHandlerOnImage createTouchOnImageEndHandler(final TouchOnImageEndHandler touchEndHandler) {
		return new TouchEndHandlerOnImage(touchEndHandler, touchEventsCoordinates);
	}
}
