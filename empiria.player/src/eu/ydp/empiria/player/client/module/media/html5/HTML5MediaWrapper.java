package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.media.client.MediaBase;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;

/**
 * Wrapper dla elemntow audio i video html5
 *
 */
public class HTML5MediaWrapper implements MediaWrapper<MediaBase> {
	MediaBase mediaBase;
	String uniqId = null;
	HTML5MediaAvailableOptions availableOptions = new HTML5MediaAvailableOptions();
	public HTML5MediaWrapper(Media mb) {
		this.mediaBase = mb.getMedia();
	}

	@Override
	public MediaAvailableOptions getMediaAvailableOptions() {
		return availableOptions;
	}

	@Override
	public MediaBase getMediaObject() {
		return mediaBase;
	}

	@Override
	public String getMediaUniqId() {
		if (uniqId == null) {
			uniqId = String.valueOf(new StringBuilder().append(System.currentTimeMillis()).append(Math.random()).toString());
		}
		return uniqId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result +  getMediaUniqId().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HTML5MediaWrapper other = (HTML5MediaWrapper) obj;
		if (getMediaUniqId() == null) {
			if (other.getMediaUniqId() != null)
				return false;
		} else if (!getMediaUniqId().equals(other.getMediaUniqId()))
			return false;
		return true;
	}

	@Override
	public double getCurrentTime() {
		return mediaBase.getCurrentTime();
	}

	@Override
	public double getDuration() {
		return mediaBase.getDuration();
	}

	@Override
	public boolean isMuted() {
		return mediaBase.isMuted();
	}

	@Override
	public double getVolume() {
		return mediaBase.getVolume();
	}
}
