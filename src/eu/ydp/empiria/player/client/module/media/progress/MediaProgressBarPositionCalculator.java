package eu.ydp.empiria.player.client.module.media.progress;

import eu.ydp.empiria.player.client.style.ComputedStyle;

class MediaProgressBarPositionCalculator {

	private final MediaProgressBar mediaProgressBar;
	private final ComputedStyle computedStyle;

	public MediaProgressBarPositionCalculator(MediaProgressBar mediaProgressBar, ComputedStyle computedStyle) {
		this.mediaProgressBar = mediaProgressBar;
		this.computedStyle = computedStyle;
	}

	boolean isRTL() {
		String direction = computedStyle.getDirectionFromBody();
		return "rtl".equalsIgnoreCase(direction);
	}

	public double calculateCurrentPosistion(int positionX) {
		int scrollWidth = mediaProgressBar.getScrollWidth();
		double mediaDuration = mediaProgressBar.getMediaWrapper().getDuration();
		double steep = mediaDuration / scrollWidth;
		positionX = getMirrorOfPositionIfTextIsRTL(positionX);
		double time = steep * positionX;
		double position = Math.min(time, mediaDuration);
		return Math.max(0, position);
	}

	private int getMirrorOfPositionIfTextIsRTL(int positionX) {
		if (isRTL()) {
			return getMirrorOfPosition(positionX);
		}
		return positionX;
	}

	private int getMirrorOfPosition(int positionX) {
		int scrollWidth = mediaProgressBar.getScrollWidth();
		return scrollWidth - positionX;
	}

	public int calculateCurrentPosistionForScroll(int positionX) {
		int scrollWidth = mediaProgressBar.getScrollWidth();
		int position = Math.max(0, Math.min(positionX, scrollWidth));
		return getMirrorOfPositionIfTextIsRTL(position);
	}

	public int getLeftPositionForAfterProgressElement(int leftOffsetForProgressButton) {
		if (isRTL()) {
			return 0;
		}
		return leftOffsetForProgressButton + getHalfWidthOfProgressBarButton();
	}

	public int getLeftPositionForBeforeProgressElement(int positionX) {
		if (isRTL()) {
			return getMirrorOfPosition(positionX) + getHalfWidthOfProgressBarButton();
		}
		return 0;
	}

	private int getHalfWidthOfProgressBarButton() {
		return mediaProgressBar.getButtonWidth() / 2;
	}

	public int getWidthForAfterProgressElement(int positionX) {
		return getMirrorOfPosition(positionX) + getHalfWidthOfProgressBarButton();
	}

}
