package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

import java.util.Map;

public class HTML5MediaEventMapper {

    @Inject
    private EventsBus eventsBus;

    private final Map<HTML5MediaEventsType, MediaEventTypes> pairMapSyncEvents = creatEventsPairMap();

    private final Map<HTML5MediaEventsType, MediaEventTypes> pairMapAsyncEvents = creatAsyncEventsPairMap();

    private Map<HTML5MediaEventsType, MediaEventTypes> creatEventsPairMap() {
        Map<HTML5MediaEventsType, MediaEventTypes> pairMap = ImmutableMap.<HTML5MediaEventsType, MediaEventTypes>builder()
                .put(HTML5MediaEventsType.canplay, MediaEventTypes.CAN_PLAY)
                .put(HTML5MediaEventsType.suspend, MediaEventTypes.SUSPEND)
                .put(HTML5MediaEventsType.ended, MediaEventTypes.ON_END)
                .put(HTML5MediaEventsType.error, MediaEventTypes.ON_ERROR)
                .put(HTML5MediaEventsType.pause, MediaEventTypes.ON_PAUSE)
                .put(HTML5MediaEventsType.volumechange, MediaEventTypes.ON_VOLUME_CHANGE)
                .put(HTML5MediaEventsType.play, MediaEventTypes.ON_PLAY)
                .build();
        return pairMap;
    }

    private Map<HTML5MediaEventsType, MediaEventTypes> creatAsyncEventsPairMap() {
        Map<HTML5MediaEventsType, MediaEventTypes> pairMap = ImmutableMap.<HTML5MediaEventsType, MediaEventTypes>builder()
                .put(HTML5MediaEventsType.durationchange, MediaEventTypes.ON_DURATION_CHANGE)
                .put(HTML5MediaEventsType.timeupdate, MediaEventTypes.ON_TIME_UPDATE)
                .build();
        return pairMap;
    }

    public void mapAndFireEvent(HTML5MediaEventsType eventType, SoundExecutorListener listener, MediaWrapper<?> mediaWrapper) {
        fireEvent(mediaWrapper, eventType);
        callListenerMethod(listener, eventType);
    }

    private void fireEvent(MediaWrapper<?> mediaWrapper, HTML5MediaEventsType eventType) {
        if (pairMapSyncEvents.containsKey(eventType)) {
            fireSyncEvent(pairMapSyncEvents.get(eventType), mediaWrapper);
        } else if (pairMapAsyncEvents.containsKey(eventType)) {
            fireAsyncEvent(pairMapAsyncEvents.get(eventType), mediaWrapper);
        }
    }

    private void callListenerMethod(SoundExecutorListener listener, HTML5MediaEventsType eventType) {
        if (listener == null) {
            return;
        }

        switch (eventType) {
            case ended:
                listener.onSoundFinished();
                break;
            case play:
                listener.onPlay();
                break;
            default:
                break;
        }
    }

    private void fireSyncEvent(MediaEventTypes type, MediaWrapper<?> mediaWrapper) {
        eventsBus.fireEventFromSource(new MediaEvent(type, mediaWrapper), mediaWrapper);
    }

    private void fireAsyncEvent(MediaEventTypes type, MediaWrapper<?> mediaWrapper) {
        eventsBus.fireAsyncEventFromSource(new MediaEvent(type, mediaWrapper), mediaWrapper);
    }
}
