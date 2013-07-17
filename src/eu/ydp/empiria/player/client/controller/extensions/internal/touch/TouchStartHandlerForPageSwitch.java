package eu.ydp.empiria.player.client.controller.extensions.internal.touch;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;

public class TouchStartHandlerForPageSwitch extends TouchEventHandler implements TouchStartHandler {
	
	public TouchStartHandlerForPageSwitch(TouchOperator operator) {
		super(operator);
	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		JsArray<Touch> touches = event.getTouches();
		processTouches(touches);
		touchOperator.setTouchReservation(false);
	}

	@Override
	protected void updateCoordinates(Touch touch) {
		touchOperator.updateStartCoordinates(touch);
		touchOperator.resetHorizontalEnd();
	}
	@Override
	protected boolean shouldSwitchPage() {
		return false;
	}
	@Override
	protected int getSwipeLength() {
		return 0;
	}
}