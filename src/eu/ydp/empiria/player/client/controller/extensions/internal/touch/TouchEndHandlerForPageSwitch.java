package eu.ydp.empiria.player.client.controller.extensions.internal.touch;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.user.client.Window;

public class TouchEndHandlerForPageSwitch extends TouchEventHandler implements TouchEndHandler {
	
	private final int minSwipeLength = 220;
	
	public TouchEndHandlerForPageSwitch(TouchOperator operator) {
		super(operator);
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		JsArray<Touch> touches = event.getChangedTouches();
		processTouches(touches);
	}

	@Override
	protected void updateCoordinates(Touch touch) {
		touchOperator.updateEndCoordinates(touch);
	}
	@Override
	protected boolean shouldSwitchPage() {
		int verticalTouchLength = Math.abs( touchOperator.getStartY() - touchOperator.getEndY());
		return (touchOperator.getEndX() > 0) && ((Window.getClientHeight() / 2.5) > verticalTouchLength);
	}
	@Override
	protected int getSwipeLength() {
		return minSwipeLength;
	}
}