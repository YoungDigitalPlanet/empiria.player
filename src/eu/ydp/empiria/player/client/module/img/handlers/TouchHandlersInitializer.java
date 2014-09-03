package eu.ydp.empiria.player.client.module.img.handlers;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.img.handlers.touch.TouchEndHandlerImpl;
import eu.ydp.empiria.player.client.module.img.handlers.touch.TouchMoveHandlerImpl;
import eu.ydp.empiria.player.client.module.img.handlers.touch.TouchStartHandlerImpl;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.EventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.TouchEventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.ITouchHandlerInitializer;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;

public class TouchHandlersInitializer implements ITouchHandlerInitializer<EventsCoordinates> {

	@Inject
	private TouchEventsCoordinates touchEventsCoordinates;

	@Override
	public void addTouchMoveHandler(final TouchOnMoveHandler<EventsCoordinates> touchOnMoveHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchMoveHandlerImpl(touchOnMoveHandler, touchEventsCoordinates), TouchMoveEvent.getType());
	}

	@Override
	public void addTouchStartHandler(final TouchOnStartHandler<EventsCoordinates> touchStartHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchStartHandlerImpl(touchStartHandler, touchEventsCoordinates), TouchStartEvent.getType());

	}

	@Override
	public void addTouchEndHandler(final TouchOnEndHandler<EventsCoordinates> touchEndHandler, Widget listenOn) {
		listenOn.addDomHandler(new TouchEndHandlerImpl(touchEndHandler, touchEventsCoordinates), TouchEndEvent.getType());

	}

	@Override
	public void addTouchCancelHandler(TouchOnCancelHandler<EventsCoordinates> touchCancelHandler, Widget listenOn) {
		// TODO Auto-generated method stub
	}
}
