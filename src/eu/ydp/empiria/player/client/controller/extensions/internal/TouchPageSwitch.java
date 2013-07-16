package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

@SuppressWarnings("PMD")
public class TouchPageSwitch extends InternalExtension implements FlowRequestSocketUserExtension,PlayerEventHandler {

	private int startX = 0;
	private int startY = 0; 
	private int endX = 0;
	private int endY = 0;
	private final int minSwipeLength = 220;
	private final int stackAndroidSwipeLength = 20;
	private FlowRequestInvoker flowRequestInvoker;
	private boolean touchReservation = false;

	public TouchPageSwitch() {
		RootPanel rp = RootPanel.get();
		rp.addDomHandler(new TouchStartHandlerForPageSwitch(), TouchStartEvent.getType());
		rp.addDomHandler(new TouchMoveHandlerForPageSwitch(), TouchMoveEvent.getType());
		rp.addDomHandler(new TouchEndHandlerForPageSwitch(), TouchEndEvent.getType());
	}

	private void switchPage(int swipeLength) {
		if(touchReservation){
			return;
		}
		NavigationButtonDirection direction = getDirectionIfSwipe(swipeLength);

		if( direction != null ){
			FlowRequest request = NavigationButtonDirection.getRequest( direction );
			flowRequestInvoker.invokeRequest( request );
		}
	}

	private NavigationButtonDirection getDirectionIfSwipe(int swipeLength) {
		NavigationButtonDirection direction = null;

		if (wasSwipeToLeft(swipeLength)) {
			direction = NavigationButtonDirection.PREVIOUS;
		} else if (wasSwipeToRight(swipeLength)) {
			direction = NavigationButtonDirection.NEXT;
		}
		return direction;
	}

	private boolean wasSwipeToLeft(int swipeLength) {
		return (endX > startX) && (swipeLength < endX - startX);
	}
	private boolean wasSwipeToRight(int swipeLength) {
		return (startX > endX) && (swipeLength < startX - endX);
	}

	@Override
	public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
		flowRequestInvoker = fri;
	}

	@Override
	public void init() {//NOPMD
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		//patrzymy czy jakis widget nie chce przejac tego zdarzenia na wylacznosc
		if(event.getType()==PlayerEventTypes.TOUCH_EVENT_RESERVATION){
			touchReservation = true;
		}

	}



	private class TouchStartHandlerForPageSwitch extends TouchEventHandler implements TouchStartHandler {
		@Override
		public void onTouchStart(TouchStartEvent event) {
			JsArray<Touch> touches = event.getTouches();
			processTouches(touches);
			touchReservation = false;
		}

		@Override
		protected void updateCoordinates(Touch touch) {
			updateStartCoordinates(touch);
			endX = -1;
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

	private class TouchEndHandlerForPageSwitch extends TouchEventHandler implements TouchEndHandler {
		@Override
		public void onTouchEnd(TouchEndEvent event) {
			JsArray<Touch> touches = event.getChangedTouches();
			processTouches(touches);
		}

		@Override
		protected void updateCoordinates(Touch touch) {
			updateEndCoordinates(touch);
		}
		@Override
		protected boolean shouldSwitchPage() {
			return (endX > 0 && (Window.getClientHeight() / 2.5) > (startY > endY ? startY - endY : endY - startY));
		}
		@Override
		protected int getSwipeLength() {
			return minSwipeLength;
		}
	}

	private class TouchMoveHandlerForPageSwitch extends TouchEventHandler implements TouchMoveHandler {
		@Override
		public void onTouchMove(TouchMoveEvent event) {
			JsArray<Touch> touches = event.getTouches();
			processTouches(touches);
		}

		@Override
		protected void updateCoordinates(Touch touch) {
			updateEndCoordinates(touch);
		}
		@Override
		protected boolean shouldSwitchPage() {
			return (UserAgentChecker.isStackAndroidBrowser() && endX > 0);
		}
		@Override
		protected int getSwipeLength() {
			return stackAndroidSwipeLength;
		}
	}

	private abstract class TouchEventHandler {
		protected void processTouches(JsArray<Touch> touches) {
			if (touchIsInArray(touches)) {
				updateCoordinates( touches.get(0) );
			}
			if ( shouldSwitchPage() ) {
				switchPage( getSwipeLength() );
			}
		}
		protected void updateStartCoordinates(Touch touch){
			startX = touch.getPageX();
			startY = touch.getScreenY();
		}
		protected void updateEndCoordinates(Touch touch){
			endX = touch.getPageX();
			endY = touch.getScreenY();
		}
		private boolean touchIsInArray( JsArray<Touch> touches ) {
			return ( touches != null ) && ( touches.length() > 0 );
		}
		protected abstract void updateCoordinates(Touch touch);
		protected abstract boolean shouldSwitchPage();
		protected abstract int getSwipeLength();
	}
}
