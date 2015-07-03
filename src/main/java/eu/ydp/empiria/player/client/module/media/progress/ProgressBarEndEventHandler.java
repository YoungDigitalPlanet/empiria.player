package eu.ydp.empiria.player.client.module.media.progress;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;

import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.PAUSE;

public class ProgressBarEndEventHandler extends AbstractMediaEventHandler {

    private final MediaProgressBarImpl progressBar;
    private final EventsBus eventsBus;

    @Inject
    public ProgressBarEndEventHandler(@Assisted MediaProgressBarImpl progressBar, EventsBus eventsBus) {
        this.progressBar = progressBar;
        this.eventsBus = eventsBus;

    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        double steep = progressBar.getScrollWidth() / progressBar.getMediaWrapper().getDuration();
        progressBar.moveScroll((int) (steep * progressBar.getMediaWrapper().getCurrentTime()));
        eventsBus.fireEventFromSource(new MediaEvent(PAUSE, progressBar.getMediaWrapper()), progressBar.getMediaWrapper());
    }

}
