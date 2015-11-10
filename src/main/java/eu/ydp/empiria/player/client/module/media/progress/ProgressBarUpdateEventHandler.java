package eu.ydp.empiria.player.client.module.media.progress;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.view.ConsoleLog;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.ON_FULL_SCREEN_SHOW_CONTROLS;
import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.ON_STOP;

public class ProgressBarUpdateEventHandler implements MediaEventHandler {
    Set<MediaEventTypes> fastUpdateEvents = new HashSet<MediaEventTypes>(Arrays.asList(new MediaEventTypes[]{ON_FULL_SCREEN_SHOW_CONTROLS, ON_STOP,
            MediaEventTypes.ON_DURATION_CHANGE}));
    // -1 aby przy pierwszym zdarzeniu pokazal sie timer
    private int lastTime = -1;
    private final MediaProgressBarImpl progressBar;
    private final ProgressUpdateLogic progressUpdateLogic;

    @Inject
    public ProgressBarUpdateEventHandler(@Assisted MediaProgressBarImpl progressBar, ProgressUpdateLogic progressUpdateLogic) {
        this.progressBar = progressBar;
        this.progressUpdateLogic = progressUpdateLogic;
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
//        ConsoleLog.consoleLog("on media event progress bar event handler: "+event);
        if (progressBar.isMediaReady() && !progressBar.isPressed()) {
            double currentTime = progressBar.getMediaWrapper().getCurrentTime();
//            ConsoleLog.consoleLog("currentTime: "+currentTime);
//            ConsoleLog.consoleLog("getMediaWrapper progress update: "+progressBar.getMediaWrapper());

            if (progressUpdateLogic.isReadyToUpdate(currentTime, lastTime) || fastUpdateEvents.contains(event.getType())) {
                lastTime = (int) progressBar.getMediaWrapper().getCurrentTime();
                double steep = progressBar.getScrollWidth() / progressBar.getMediaWrapper().getDuration();
                progressBar.moveScroll((int) (steep * lastTime));
            }
        }
    }

    public void resetCurrentTime() {
        lastTime = -1;
    }
}
