package eu.ydp.empiria.player.client.util.position;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Touch;

public class PositionHelper {
	/**
	 * zwraca relatywna pozycje Y zdarzenia do elementu target
	 *
	 * @param event
	 * @param target
	 * @return
	 */
	public int getPositionY(NativeEvent event, Element target) {
		Touch touch = getTouch(event);
		int positionY = 0;
		if (touch == null) {
			positionY = event.getClientY() - target.getAbsoluteTop() + target.getScrollTop() + target.getOwnerDocument().getScrollTop();
		} else {
			positionY = touch.getRelativeY(target);
		}
		return positionY;
	}

	/**
	 * zwraca relatywna pozycje X zdarzenia do elementu target
	 *
	 * @param event
	 * @param target
	 * @return
	 */
	public int getPositionX(NativeEvent event, Element target) {
		Touch touch = getTouch(event);
		int positionX = 0;
		if (touch == null) {
			positionX = event.getClientX() - target.getAbsoluteLeft() + target.getScrollLeft() + target.getOwnerDocument().getScrollLeft();
		} else {
			positionX = touch.getRelativeX(target);
		}
		return positionX;
	}

	private Touch getTouch(NativeEvent event) {
		JsArray<Touch> touches = event.getChangedTouches();
		Touch touch = null;
		if (touches != null && touches.length() == 1) {
			touch = touches.get(0);
		}
		return touch;
	}
}
