package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class TouchReservationHandler implements HandlerRegistration {

	private final HandlerRegistration handlerRegistration;

	@Inject
	public TouchReservationHandler(@Assisted IsWidget widget, final EventsBus eventsBus) {
		handlerRegistration = widget.asWidget().addDomHandler(new TouchStartHandler() {

			@Override
			public void onTouchStart(TouchStartEvent event) {
				eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));

			}
		}, TouchStartEvent.getType());
	}

	@Override
	public void removeHandler() {
		if (handlerRegistration != null) {
			handlerRegistration.removeHandler();
		}

	}
}
