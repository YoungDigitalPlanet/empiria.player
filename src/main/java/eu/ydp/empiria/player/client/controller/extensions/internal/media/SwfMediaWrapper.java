package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.gwtflashmedia.client.FlashMedia;
import eu.ydp.empiria.gwtflashmedia.client.event.*;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

/**
 * Wrapper dla mediow swf
 */
public class SwfMediaWrapper implements MediaWrapper<Widget>, FlashMediaPlayheadUpdateHandler, FlashMediaMuteChangeHandler, FlashMediaVolumeChangeHandler,
        FlashMediaMetadataHandler, FlashMediaStopHandler, FlashMediaLoadedHandler {

    protected MediaAvailableOptions swfOptions = new SwfMediaAvailableOptions();
    protected double duration = 0;
    protected double currentTime = 0;
    protected double volume = 0;
    protected boolean muted = false;
    protected Widget mediaWidget = new FlowPanel();
    protected FlashMedia flashMedia;
    protected boolean ready = false;

    @Override
    public MediaAvailableOptions getMediaAvailableOptions() {
        return swfOptions;
    }

    @Override
    public Widget getMediaObject() {
        return mediaWidget;
    }

    public void setFlashMedia(FlashMedia flashMedia) {
        this.flashMedia = flashMedia;
    }

    public FlashMedia getFlashMedia() {
        return flashMedia;
    }

    public void setMediaWidget(Widget mediaWidget) {
        this.mediaWidget = mediaWidget;
    }

    @Override
    public String getMediaUniqId() {
        return null;
    }

    @Override
    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public boolean canPlay() {
        return ready;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public void onFlashSoundPositionChange(FlashMediaPlayheadUpdateEvent event) {
        currentTime = event.getPlayheadTime() * .001f;
    }

    @Override
    public void onFlashSoundMuteChange(FlashMediaMuteChangeEvent event) {
        muted = event.isMute();
    }

    @Override
    public void onFlashSoundVolumeChange(FlashMediaVolumeChangeEvent event) {
        volume = event.getVolume() * .01f;
    }

    @Override
    public void onFlashMediaMetadataEvent(FlashMediaMetadataEvent event) {
        duration = event.getDuration() * .001f;
    }

    @Override
    public void onFlashSoundStop(FlashMediaStopEvent event) {
        currentTime = event.getPlayheadTime() * .001f;
    }

    @Override
    public void onFlashSoundLoaded(FlashMediaLoadedEvent event) {
        ready = true;
    }

}
