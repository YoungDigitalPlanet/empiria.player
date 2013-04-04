package eu.ydp.empiria.player.client.controller.multiview.touch;

public class TouchModel {
	private int startX;
	private int endY;
	private int endX;
	private int startY;
	private int lastEndX;
	private int startScrollTopPossition;
	private boolean touchLock;
	private boolean touchReservation;
	private boolean swipeStarted;

	public void setTouchLock(boolean touchLock) {
		this.touchLock = touchLock;
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

	public boolean isTouchLock() {
		return touchLock;
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
}
