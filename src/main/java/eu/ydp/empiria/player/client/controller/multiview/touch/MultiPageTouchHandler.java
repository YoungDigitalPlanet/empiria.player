package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.multiview.IMultiPageController;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.TouchHandler;
import eu.ydp.gwtutil.client.event.TouchEventReader;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class MultiPageTouchHandler implements TouchHandler {

	private IMultiPageController multiPageController;
	private final UserAgentUtil userAgentUtil;
	private final TouchEventReader touchEventReader;
	private final TouchController touchController;
	private final ITouchEndTimer touchEndTimer;

	@Inject
	public MultiPageTouchHandler(UserAgentUtil userAgentUtil, TouchEventReader touchEventReader, TouchController touchController,
			TouchEndTimerFactory touchEndTimerFactory) {

		this.userAgentUtil = userAgentUtil;
		this.touchEventReader = touchEventReader;
		this.touchController = touchController;
		this.touchEndTimer = touchEndTimerFactory.createTimer(this);
	}

	public void setMultiPageController(IMultiPageController multiPageController) {
		this.multiPageController = multiPageController;
	}

	public void touchOnEndTimer() {
		if (userAgentUtil.isStackAndroidBrowser()) {
			multiPageController.animatePageSwitch();
			touchController.resetTouchModel();
		}
	}

	@Override
	public void onTouchEvent(TouchEvent event) {
		switch (event.getType()) {
		case TOUCH_START:
			onTouchStart(event.getNativeEvent());
			break;
		case TOUCH_END:
			onTouchEnd(event.getNativeEvent());
			break;
		case TOUCH_MOVE:
			onTouchMove(event.getNativeEvent());
			break;
		default:
			break;
		}
	}

	private void onTouchStart(NativeEvent event) {

		if (!touchController.canSwype(multiPageController)) {
			return;
		}

		if (touchController.isSwypeStarted()) {
			return;
		}

		touchController.updateOnTouchStart(event);

		touchEndTimer.cancel();
		multiPageController.resetFocusAndStyles();
	}

	private void onTouchMove(NativeEvent event) {
		touchEndTimer.cancel();

		if (!touchController.canMove(multiPageController)) {
			return;
		}

		// nie zawsze wystepuje event touchend na androidzie - emulacja
		// zachownaia
		touchController.updateEndPoint(event);

		if (touchController.isReadyToStartAnimation()) {
			startAnimation(event);
		} else {
			touchController.setVerticalSwipeDetected(true);
		}
	}

	private void startAnimation(NativeEvent event) {
		touchEventReader.preventDefault(event);
		touchEndTimer.schedule(MultiPageController.TOUCH_END_TIMER_TIME);

		multiPageController.move(touchController.isSwipeRight(), touchController.getSwypePercentLength());
		touchController.updateAfterSwypeDetected();
	}

	private void onTouchEnd(NativeEvent event) {
		touchEndTimer.cancel();

		if (touchController.isSwipeStarted()) {
			touchEventReader.preventDefault(event);
			touchController.updateOnTouchEnd(event);

			if (touchController.canSwitchPage()) {
				multiPageController.switchPage();
			} else {
				multiPageController.animatePageSwitch();
			}
			touchController.setSwypeStarted(false);
		}

		touchController.resetTouchModel();
		multiPageController.resetFocusAndStyles();
	}

	public void setTouchReservation(boolean touchReservation) {
		touchController.setTouchReservation(touchReservation);
	}

	public boolean getTouchReservation() {
		return touchController.isTouchReservation();
	}

	public NavigationButtonDirection getDirection() {
		return touchController.getDirection();
	}

	public void resetModelAndTimer() {
		touchEndTimer.cancel();
		touchController.resetTouchModel();
	}
}