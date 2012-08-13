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
	protected MediaBase mediaBase;
	protected String uniqId = null;
	protected HTML5MediaAvailableOptions availableOptions = new HTML5MediaAvailableOptions();
	public HTML5MediaWrapper(Media media) {
		this.mediaBase = media.getMedia();
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

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result +  getMediaUniqId().hashCode();
		return result;
	}

	@Override
	@SuppressWarnings("PMD")
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		HTML5MediaWrapper other = (HTML5MediaWrapper) obj;
		if (getMediaUniqId() == null) {
			if (other.getMediaUniqId() != null) {
				return false;
			}
		} else if (!getMediaUniqId().equals(other.getMediaUniqId())) {
			return false;
		}
		return true;
	}
}
