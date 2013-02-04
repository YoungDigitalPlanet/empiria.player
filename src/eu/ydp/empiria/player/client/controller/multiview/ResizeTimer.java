package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.user.client.Timer;

public class ResizeTimer extends Timer {

	private final ResizeContinousUpdater resizeContinousUpdater;

	public ResizeTimer(ResizeContinousUpdater resizeContinousUpdater) {
		this.resizeContinousUpdater = resizeContinousUpdater;
	}
	
	public void cancelAndReset() {
		super.cancel();
		resizeContinousUpdater.reset();
	}

	@Override
	public void run() {
		int rescheduleTime = resizeContinousUpdater.runContinousResizeUpdateAndReturnRescheduleTime();
		
		if(rescheduleTime > 0){
			schedule(rescheduleTime);
		}
	}

}
