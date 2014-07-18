package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.user.client.Timer;

public class ResizeTimer extends Timer {

	private final ResizeContinuousUpdater resizeContinuousUpdater;

	public ResizeTimer(ResizeContinuousUpdater resizeContinuousUpdater) {
		this.resizeContinuousUpdater = resizeContinuousUpdater;
	}

	public void cancelAndReset() {
		super.cancel();
		resizeContinuousUpdater.reset();
	}

	@Override
	public void run() {
		int rescheduleTime = resizeContinuousUpdater.runContinousResizeUpdateAndReturnRescheduleTime();

		if (rescheduleTime > 0) {
			schedule(rescheduleTime);
		}
	}

}
