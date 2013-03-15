package eu.ydp.empiria.player.client.module.media.external;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Video;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;

public class FullscreenVideoMediaWrapper implements MediaWrapper<Widget> {

	private static final int DURATION_PERCENT_MAX = 100;
	@Inject private UniqueIdGenerator idGnerator;
	@Inject private ExternalFullscreenVideoMediaAvailableOptions availableOptions; 
	
	private String id;
	private double time;
	private Video mediaObject;
	
	@Override
	public MediaAvailableOptions getMediaAvailableOptions() {
		return availableOptions;
	}

	@Override
	public Widget getMediaObject() {
		return mediaObject.asWidget();
	}
	
	public void setMediaObject(Video mediaObject) {
		this.mediaObject = mediaObject;
	}
	
	public void setPoster(String url){
		mediaObject.setPoster(url);
	}

	@Override
	public String getMediaUniqId() {
		if (id == null){
			id = idGnerator.createUniqueId();
		}
		return id;
	}

	@Override
	public double getCurrentTime() {
		return time;
	}

	@Override
	public double getDuration() {
		return DURATION_PERCENT_MAX;
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

	public void setCurrentTime(double time) {
		this.time = time;
	}
}
