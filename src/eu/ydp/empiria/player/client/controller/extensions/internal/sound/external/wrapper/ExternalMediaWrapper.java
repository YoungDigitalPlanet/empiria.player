package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.wrapper;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.ExternalMediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;

public class ExternalMediaWrapper implements MediaWrapper<Widget> {
	
	@Inject private UniqueIdGenerator idGenerator;
	
	private String uniqId;
	private double durationSeconds;
	private double currentTimeSeconds;

	@Override
	public MediaAvailableOptions getMediaAvailableOptions() {
		return new ExternalMediaAvailableOptions();
	}

	@Override
	public Widget getMediaObject() {
		return new SimplePanel();
	}

	@Override
	public String getMediaUniqId() {
		if (uniqId == null) {
			uniqId = idGenerator.createUniqueId();
		}
		return uniqId;
	}

	@Override
	public double getCurrentTime() {
		return currentTimeSeconds;
	}

	@Override
	public double getDuration() {
		return durationSeconds;
	}

	@Override
	public boolean isMuted() {
		return false;
	}

	@Override
	public double getVolume() {
		return 0;
	}

	@Override
	public boolean canPlay() {
		return true;
	}
	
	public void setDuration(double durationSeconds){
		this.durationSeconds = durationSeconds;
	}
	
	public void setCurrentTime(double currentTimeSeconds){
		this.currentTimeSeconds = currentTimeSeconds;
	}

}
