package eu.ydp.empiria.player.client.module.img.handlers;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.img.handlers.touch.TouchEndHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.handlers.touch.TouchMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.handlers.touch.TouchStartHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.TouchToImageEvent;

public class TouchHandlersOnImageInitializer implements ITouchHandlerOnImageInitializer {

	@Inject
	private TouchToImageEvent touchEventsCoordinates;

	@Override
	public void addTouchMoveHandler(final TouchOnImageMoveHandler touchOnMoveHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchMoveHandlerOnImage(touchOnMoveHandler, touchEventsCoordinates), TouchMoveEvent.getType());
	}

	@Override
	public void addTouchStartHandler(final TouchOnImageStartHandler touchStartHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchStartHandlerOnImage(touchStartHandler, touchEventsCoordinates), TouchStartEvent.getType());

	}

	@Override
	public void addTouchEndHandler(final TouchOnImageEndHandler touchEndHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchEndHandlerOnImage(touchEndHandler, touchEventsCoordinates), TouchEndEvent.getType());

	}
}
