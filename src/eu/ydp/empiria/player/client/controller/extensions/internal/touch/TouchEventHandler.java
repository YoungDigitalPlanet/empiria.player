package eu.ydp.empiria.player.client.controller.extensions.internal.touch;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;

public abstract class TouchEventHandler {
	
	protected TouchOperator touchOperator;
	
	public TouchEventHandler( TouchOperator operator ){
		touchOperator = operator;
	}
	
	protected void processTouches(JsArray<Touch> touches) {
		if (touchIsInArray(touches)) {
			updateCoordinates( touches.get(0) );
		}
		if ( shouldSwitchPage() ) {
			touchOperator.switchPage( getSwipeLength() );
		}
	}
	
	private boolean touchIsInArray( JsArray<Touch> touches ) {
		return ( touches != null ) && ( touches.length() > 0 );
	}
	protected abstract void updateCoordinates(Touch touch);
	protected abstract boolean shouldSwitchPage();
	protected abstract int getSwipeLength();
}