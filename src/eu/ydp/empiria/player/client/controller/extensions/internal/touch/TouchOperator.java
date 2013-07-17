package eu.ydp.empiria.player.client.controller.extensions.internal.touch;

import com.google.gwt.dom.client.Touch;

public interface TouchOperator {
	void updateStartCoordinates(Touch touch);
	void updateEndCoordinates(Touch touch);
	void switchPage(int swipeLength);
	void setTouchReservation(boolean touchReservation);
	 void resetHorizontalEnd();
	int getStartX();
	int getStartY();
	int getEndX();
	int getEndY();
}
