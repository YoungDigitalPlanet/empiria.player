package eu.ydp.empiria.player.client.module.media.html5;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.HTML5MediaExecutor;
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
	protected final EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();
	protected HTML5MediaAvailableOptions availableOptions = new HTML5MediaAvailableOptions();
	protected boolean ready = false;
	protected List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();
	protected HTML5MediaExecutor mediaExecutor;
	protected AttachHandlerImpl attachHandlerImpl;
	protected Media media;
	private final HTML5VideoForcePosterOnIOSHack html5VideoForcePosterHackForIOS;

	public HTML5MediaWrapper(Media media) {
		this.media = media;		
		
		setMediaObject(media.getMedia());
		if (media instanceof Video) {
			attachHandlerImpl = new AttachHandlerImpl();
			attachHandlerImpl.setMediaBase(mediaBase);
			attachHandlerImpl.setMediaExecutor(mediaExecutor);
			attachHandlerImpl.setMediaWrapper(this);
			mediaBase.addAttachHandler(attachHandlerImpl);
		}
		
		html5VideoForcePosterHackForIOS = new HTML5VideoForcePosterOnIOSHack();
		html5VideoForcePosterHackForIOS.init(this);
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
		handlerRegistrations.add(eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), this, this, new CurrentPageScope()));
		handlerRegistrations.add(eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.SUSPEND), this, this, new CurrentPageScope()));
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
		MediaEventTypes eventType = event.getType();
		if (eventType == MediaEventTypes.ON_DURATION_CHANGE) {
			ready = true;
			removeHandler();
		}
		
		if (eventType == MediaEventTypes.SUSPEND || eventType == MediaEventTypes.CAN_PLAY) {
			applyHacksOnMediaReady();
		}
	}

	private void applyHacksOnMediaReady() {
		Widget parent = mediaBase.getParent();
		if (parent != null) {
			Element parentElement = parent.getElement();
			html5VideoForcePosterHackForIOS.applayHackIfRequired(parentElement, mediaExecutor.getBaseMediaConfiguration());
		}
	}

	private void removeHandler() {		
		for (HandlerRegistration handlerRegistration : handlerRegistrations) {
			handlerRegistration.removeHandler();
		}
		html5VideoForcePosterHackForIOS.clean();
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

	protected Media getMedia() {
		return media;
	}

}
