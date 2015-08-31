package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SingleMediaPlayback;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnectorListener;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.MediaParams;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.MediaStatus;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.wrapper.ExternalMediaProxy;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.wrapper.MediaProxy;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

import java.util.Map;

@Singleton
public class ExternalMediaEngine implements MediaConnectorListener, EmulatedTimeUpdateListener {

    public static final int PLAY_INITIAL_TIME = 0;

    @Inject
    private MediaConnector connector;
    @Inject
    private EventsBus eventsBus;
    @Inject
    private SingleMediaPlayback singleMediaPlayback;
    @Inject
    private ConnectorTimeResolutionToSecondsConverter timeConverter;
    @Inject
    private ExternalMediaUpdateTimerEmulator timerEmulator;

    private Map<String, ExternalMediaProxy> proxiesByMediaId = Maps.newHashMap();

    public void addMediaProxy(ExternalMediaProxy proxy) {
        proxiesByMediaId.put(proxy.getMediaWrapper().getMediaUniqId(), proxy);
    }

    public void init(MediaWrapper<Widget> wrapper, Iterable<String> sources) {
        connector.init(wrapper.getMediaUniqId(), sources);
        timerEmulator.init(this);
    }

    public void play(MediaWrapper<Widget> wrapper) {
        connector.play(wrapper.getMediaUniqId());
    }

    public void stop(MediaWrapper<Widget> wrapper) {
        String mediaUniqId = wrapper.getMediaUniqId();
        connector.pause(mediaUniqId);
        connector.seek(mediaUniqId, PLAY_INITIAL_TIME);
    }

    public void pause(MediaWrapper<Widget> wrapper) {
        connector.pause(wrapper.getMediaUniqId());
    }

    public void setCurrentTime(MediaWrapper<Widget> wrapper, double timeSeconds) {
        int timeMillis = timeConverter.fromSeconds(timeSeconds);
        connector.seek(wrapper.getMediaUniqId(), timeMillis);
    }

    public void pauseCurrent() {
        if (singleMediaPlayback.isPlaybackPresent()) {
            String id = singleMediaPlayback.getCurrentMediaId();
            connector.pause(id);
        }
    }

    @Override
    public void onReady(String id, MediaParams params) {
        fireMediaReadyEvent(id, params);
    }

    @Override
    public void onPlay(String id) {
        if (singleMediaPlayback.isPlaybackPresent()) {
            String currMediaId = singleMediaPlayback.getCurrentMediaId();
            onPause(currMediaId);
        }
        singleMediaPlayback.setCurrentMediaId(id);
        fireMediaEvent(id, MediaEventTypes.ON_PLAY);
        startEmulatedTimer(id);
    }

    private void startEmulatedTimer(String id) {
        ExternalMediaProxy proxy = proxiesByMediaId.get(id);
        double timeSeconds = proxy.getMediaWrapper().getCurrentTime();
        int timeMillis = timeConverter.fromSeconds(timeSeconds);
        timerEmulator.run(timeMillis);
    }

    @Override
    public void onPause(String id) {
        singleMediaPlayback.clearCurrentMediaIdIfEqual(id);
        timerEmulator.stop();
        fireMediaEvent(id, MediaEventTypes.ON_PAUSE);
    }

    @Override
    public void onEnd(String id) {
        singleMediaPlayback.clearCurrentMediaIdIfEqual(id);
        timerEmulator.stop();
        fireMediaEvent(id, MediaEventTypes.ON_END);
    }

    @Override
    public void onTimeUpdate(String id, MediaStatus status) {
        timerEmulator.stop();
        fireMediaTimeUpdateEvent(id, status.getCurrentTimeMillis());
        if (singleMediaPlayback.isCurrentPlayback(id)) {
            timerEmulator.run(status.getCurrentTimeMillis());
        }
    }

    @Override
    public void emulatedTimeUpdate(MediaStatus status) {
        if (singleMediaPlayback.isPlaybackPresent()) {
            fireMediaTimeUpdateEvent(singleMediaPlayback.getCurrentMediaId(), status.getCurrentTimeMillis());
        }
    }

    private void fireMediaEvent(String id, MediaEventTypes type) {
        if (isValidMediaId(id)) {
            MediaWrapper<Widget> wrapper = findMediaWrapper(id);
            eventsBus.fireEventFromSource(new MediaEvent(type, wrapper), wrapper);
        }
    }

    private boolean isValidMediaId(String id) {
        return proxiesByMediaId.containsKey(id);
    }

    private MediaWrapper<Widget> findMediaWrapper(String id) {
        MediaProxy proxy = proxiesByMediaId.get(id);
        MediaWrapper<Widget> wrapper = proxy.getMediaWrapper();
        return wrapper;
    }

    private void fireMediaReadyEvent(String id, MediaParams params) {
        if (isValidMediaId(id)) {
            ExternalMediaProxy proxy = proxiesByMediaId.get(id);
            double durationSeconds = timeConverter.toSeconds(params.getDurationMillis());
            proxy.setDuration(durationSeconds);
            MediaWrapper<Widget> wrapper = proxy.getMediaWrapper();
            eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_DURATION_CHANGE, wrapper), wrapper);
        }
    }

    private void fireMediaTimeUpdateEvent(String id, int timeMillis) {
        if (isValidMediaId(id)) {
            ExternalMediaProxy proxy = proxiesByMediaId.get(id);
            double timeSeconds = timeConverter.toSeconds(timeMillis);
            proxy.setCurrentTime(timeSeconds);
            MediaWrapper<Widget> wrapper = proxy.getMediaWrapper();
            MediaEvent event = new MediaEvent(MediaEventTypes.ON_TIME_UPDATE, wrapper);
            event.setCurrentTime(timeSeconds);
            eventsBus.fireEventFromSource(event, wrapper);
        }
    }
}
