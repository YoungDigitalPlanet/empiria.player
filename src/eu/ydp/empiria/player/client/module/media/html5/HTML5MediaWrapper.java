package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.MediaBase;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.HTML5MediaExecutor;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.module.object.impl.Video;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

/**
 * Wrapper dla elemntow audio i video html5
 *
 */
public class HTML5MediaWrapper implements MediaWrapper<MediaBase>, MediaEventHandler {
	protected MediaBase mediaBase;
	protected String uniqId = null;
	protected final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	protected HTML5MediaAvailableOptions availableOptions = new HTML5MediaAvailableOptions();
	protected boolean ready = false;
	protected HandlerRegistration handlerRegistration;
	protected HTML5MediaExecutor mediaExecutor;
	protected AttachHandlerImpl attachHandlerImpl;

	public HTML5MediaWrapper(Media media) {
		setMediaObject(media.getMedia());
		if (media instanceof Video){
			attachHandlerImpl = new AttachHandlerImpl();
			attachHandlerImpl.setMediaBase(mediaBase);
			attachHandlerImpl.setMediaExecutor(mediaExecutor);
			attachHandlerImpl.setMediaWrapper(this);
			mediaBase.addAttachHandler(attachHandlerImpl);
		}
	}

	public void setMediaExecutor(HTML5MediaExecutor mediaExecutor) {
		this.mediaExecutor = mediaExecutor;
		if (attachHandlerImpl != null){
			attachHandlerImpl.setMediaExecutor(mediaExecutor);
		}
	}

	@Override
	public MediaAvailableOptions getMediaAvailableOptions() {
		return availableOptions;
	}

	@Override
	public MediaBase getMediaObject() {
		return mediaBase;
	}

	public void setMediaObject(MediaBase mediaBase) {
		this.mediaBase = mediaBase;
		mediaBase.setPreload(MediaElement.PRELOAD_METADATA);
		handlerRegistration = eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), this, this, new CurrentPageScope());
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
		double duration = mediaBase.getDuration();
		if (Double.isNaN(duration)) {
			return 0;
		} else {
			return duration;
		}
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
	public boolean canPlay() {
		return ready || mediaBase.getReadyState() != MediaElement.HAVE_NOTHING;
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		if (event.getType() == MediaEventTypes.ON_DURATION_CHANGE) {
			ready = true;
			handlerRegistration.removeHandler();
		}
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + getMediaUniqId().hashCode();
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
