package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaMetadataEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaMetadataHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaMuteChangeEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaMuteChangeHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaPlayheadUpdateEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaPlayheadUpdateHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaVolumeChangeEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaVolumeChangeHandler;
import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.gwtutil.client.debug.Debug;

/**
 * Wrapper dla mediow swf
 *
 */
public class SwfMediaWrapper implements MediaWrapper<Widget>, FlashMediaPlayheadUpdateHandler, FlashMediaMuteChangeHandler, FlashMediaVolumeChangeHandler,
		FlashMediaMetadataHandler {

	MediaAvailableOptions swfOptions = new SwfMediaAvailableOptions();
	double duration = 0;
	double currentTime = 0;
	double volume = 0;
	boolean muted = false;
	Widget mediaWidget = new FlowPanel();
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	@Override
	public MediaAvailableOptions getMediaAvailableOptions() {
		return swfOptions;
	}

	@Override
	public Widget getMediaObject() {
		return mediaWidget;
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

}
