package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Touch;

public class TouchEventReader {

	public boolean isMoreThenOneFingerTouch(NativeEvent event) {
		JsArray<Touch> touches = event.getTouches();

		if (touches == null) {
			return false;
		}

		return touches.length() > 1;
	}

	public int getX(NativeEvent event) {
		JsArray<Touch> touches = event.getTouches();

		if ((touches == null) || (touches.length() == 0)) {
			return event.getScreenX();
		}

		return touches.get(0).getPageX();
	}

	public int getScreenY(NativeEvent event) {
		JsArray<Touch> touches = event.getTouches();

		if ((touches == null) || (touches.length() == 0)) {
			return event.getScreenY();
		}

		return touches.get(0).getScreenY();
	}

	public int getFromChangedTouchesScreenY(NativeEvent event) {
		JsArray<Touch> touches = event.getChangedTouches();

		if ((touches == null) || (touches.length() == 0)) {
			return event.getScreenY();
		}

		return touches.get(0).getScreenY();
	}

	public int getFromChangedTouchesX(NativeEvent event) {
		JsArray<Touch> touches = event.getChangedTouches();

		if ((touches == null) || (touches.length() == 0)) {
			return event.getScreenX();
		}

		return touches.get(0).getPageX();
	}

	public void preventDefault(NativeEvent event) {
		event.preventDefault();
	}

}
