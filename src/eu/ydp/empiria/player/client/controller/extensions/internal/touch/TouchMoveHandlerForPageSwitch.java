package eu.ydp.empiria.player.client.controller.extensions.internal.touch;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;

import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class TouchMoveHandlerForPageSwitch extends TouchEventHandler implements TouchMoveHandler {
	
	private final int stackAndroidSwipeLength = 20;
	
	public TouchMoveHandlerForPageSwitch(TouchOperator operator) {
		super(operator);
	}
	
	@Override
	public void onTouchMove(TouchMoveEvent event) {
		JsArray<Touch> touches = event.getTouches();
		processTouches(touches);
	}

	@Override
	protected void updateCoordinates(Touch touch) {
		touchOperator.updateEndCoordinates(touch);
	}
	@Override
	protected boolean shouldSwitchPage() {
		return (UserAgentChecker.isStackAndroidBrowser() && touchOperator.getEndX() > 0);
	}
	@Override
	protected int getSwipeLength() {
		return stackAndroidSwipeLength;
	}
}
