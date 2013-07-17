package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.controller.extensions.internal.touch.TouchEndHandlerForPageSwitch;
import eu.ydp.empiria.player.client.controller.extensions.internal.touch.TouchMoveHandlerForPageSwitch;
import eu.ydp.empiria.player.client.controller.extensions.internal.touch.TouchOperator;
import eu.ydp.empiria.player.client.controller.extensions.internal.touch.TouchStartHandlerForPageSwitch;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

@SuppressWarnings("PMD")
public class TouchPageSwitch extends InternalExtension implements FlowRequestSocketUserExtension,PlayerEventHandler,TouchOperator{

	private int startX = 0;
	private int startY = 0; 
	private int endX = 0;
	private int endY = 0;

	private FlowRequestInvoker flowRequestInvoker;
	private boolean touchReservation = false;

	public TouchPageSwitch() {
		RootPanel rp = RootPanel.get();
		rp.addDomHandler(new TouchStartHandlerForPageSwitch(this), TouchStartEvent.getType());
		rp.addDomHandler(new TouchMoveHandlerForPageSwitch(this), TouchMoveEvent.getType());
		rp.addDomHandler(new TouchEndHandlerForPageSwitch(this), TouchEndEvent.getType());
	}

	public void switchPage(int swipeLength) {
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
			setTouchReservation(true);
		}
	}


	public void updateStartCoordinates(Touch touch){
		startX = touch.getPageX();
		startY = touch.getScreenY();
	}
	public void updateEndCoordinates(Touch touch){
		endX = touch.getPageX();
		endY = touch.getScreenY();
	}

	public void resetHorizontalEnd(){
		endX = -1;
	}
	
	public int getStartX() {
		return startX;
	}
	public int getStartY() {
		return startY;
	}
	public int getEndX() {
		return endX;
	}
	public int getEndY() {
		return endY;
	}

	public void setTouchReservation(boolean touchReservation) {
		this.touchReservation = touchReservation;
	}
}
