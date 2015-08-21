package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ResizeTimer extends Timer {

    private final ResizeContinuousUpdater resizeContinuousUpdater;

    @Inject
    public ResizeTimer(ResizeContinuousUpdater resizeContinuousUpdater) {
        this.resizeContinuousUpdater = resizeContinuousUpdater;
    }

    public void cancelAndReset() {
        super.cancel();
        resizeContinuousUpdater.reset();
    }

    @Override
    public void run() {
        int rescheduleTime = resizeContinuousUpdater.runContinuousResizeUpdateAndReturnRescheduleTime();

        if (rescheduleTime > 0) {
            schedule(rescheduleTime);
        }
    }

}
