package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.user.client.Timer;

public class TouchEndTimerFactory {
	public ITouchEndTimer createTimer(final MultiPageTouchHandler multiPageTouchHandler) {
		TouchEndTimer touchEndTimer = new TouchEndTimer() {
			@Override
			public void run() {
				multiPageTouchHandler.touchOnEndTimer();
			}

		};

		return touchEndTimer;
	}

	abstract class TouchEndTimer extends Timer implements ITouchEndTimer {
	}
}
