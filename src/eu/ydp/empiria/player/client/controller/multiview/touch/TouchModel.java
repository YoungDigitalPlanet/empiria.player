package eu.ydp.empiria.player.client.controller.multiview.touch;

public class TouchModel {
	private int startX;
	private int endY;
	private int endX;
	private int startY;
	private int lastEndX;
	private int startScrollTopPossition;
	private boolean multiTouch;
	private boolean touchReservation;
	private boolean swipeStarted;
	private boolean swypeLock;
	private boolean verticalSwipeDetected;

	public void setMultiTouch(boolean multiTouch) {
		this.multiTouch = multiTouch;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public int getLastEndX() {
		return lastEndX;
	}

	public void setLastEndX(int lastEndX) {
		this.lastEndX = lastEndX;
	}

	public int getStartScrollTopPossition() {
		return startScrollTopPossition;
	}

	public void setStartScrollTopPossition(int startScrollTopPossition) {
		this.startScrollTopPossition = startScrollTopPossition;
	}

	public boolean isMultiTouch() {
		return multiTouch;
	}

	public boolean isTouchReservation() {
		return touchReservation;
	}

	public void setTouchReservation(boolean touchReservation) {
		this.touchReservation = touchReservation;
	}

	public boolean isSwipeStarted() {
		return swipeStarted;
	}

	public void setSwipeStarted(boolean swipeStarted) {
		this.swipeStarted = swipeStarted;
	}

	public boolean isSwypeLock() {
		return swypeLock;
	}

	public void setSwypeLock(boolean swypeLock) {
		this.swypeLock = swypeLock;
	}

	public boolean isVerticalSwipeDetected() {
		return verticalSwipeDetected;
	}

	public void setVerticalSwipeDetected(boolean verticalSwipeDetected) {
		this.verticalSwipeDetected = verticalSwipeDetected;
	}
}
