package eu.ydp.empiria.player.client.controller.multiview;

public interface IMultiPageController {

	void animatePageSwitch();

	boolean isAnimationRunning();

	boolean isZoomed();

	void move(boolean swipeRight, float f);

	void resetFocusAndStyles();

	void switchPage();

	void detachAttachPanels();

	void reset();

	void setVisiblePage(int pageNumber);
}
