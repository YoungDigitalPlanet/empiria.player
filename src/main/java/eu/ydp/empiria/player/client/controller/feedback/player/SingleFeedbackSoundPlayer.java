package eu.ydp.empiria.player.client.controller.feedback.player;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5AudioMediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class SingleFeedbackSoundPlayer implements MediaEventHandler {

    private EventsBus eventsBus;
    private MediaWrapper<?> mediaWrapper;
    private Provider<HTML5MediaForFeedbacksAvailableOptions> optionsProvider;

    private boolean playing = false;
    protected boolean playAfterStop = false;

    @Inject
    public SingleFeedbackSoundPlayer(@Assisted MediaWrapper<?> mediaWrapper, EventsBus eventsBus, Provider<HTML5MediaForFeedbacksAvailableOptions> optionsProvider) {
        this.mediaWrapper = mediaWrapper;
        this.eventsBus = eventsBus;
        this.optionsProvider = optionsProvider;
        overrideWrapperOptionsIfNeeded(mediaWrapper);
        eventsBus.addHandlerToSource(MediaEvent.getTypes(MediaEventTypes.ON_STOP, MediaEventTypes.ON_PAUSE, MediaEventTypes.ON_PLAY), mediaWrapper, this);

    }

    private void overrideWrapperOptionsIfNeeded(MediaWrapper<?> mediaWrapper) {
        if (mediaWrapper instanceof HTML5AudioMediaWrapper) {
            HTML5MediaForFeedbacksAvailableOptions options = optionsProvider.get();
            ((HTML5AudioMediaWrapper) mediaWrapper)
                    .setMediaAvailableOptions(options);
        }

    }

    protected void firePlayEvent(final MediaWrapper<?> mediaWrapper) {
        eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY, mediaWrapper), mediaWrapper);
    }

    protected void fireStopEvent(final MediaWrapper<?> mediaWrapper) {
        eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.STOP, mediaWrapper), mediaWrapper);
    }

    public void play() {
        if (isPlayed()) {
            playAfterStop = true;
            fireStopEvent(mediaWrapper);
        } else {
            firePlayEvent(mediaWrapper);
        }
    }

    protected void playIfRequired() {
        if (playAfterStop) {
            firePlayEvent(mediaWrapper);
            playAfterStop = false;
        }
    }

    protected boolean isPlayed() {
        return playing;
    }

    public void setPlayed(boolean isPlayed) {
        this.playing = isPlayed;
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        switch (event.getType()) {
            case ON_PAUSE:
            case ON_STOP:
                setPlayed(false);
                playIfRequired();
                break;
            case ON_PLAY:
                setPlayed(true);
                break;
            default:
                break;
        }
    }
}
