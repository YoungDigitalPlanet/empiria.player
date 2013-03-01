package eu.ydp.empiria.player.client.module.media.html5;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.MediaBase;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.HTML5MediaExecutor;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

/**
 * Wrapper dla elemntow audio i elementow wspolnych audio i video html5
 * TODO: wydzielic HTML5AudioMediaWrapper a ten zostawic jako HTML5MediaWrapperBase
 */
public class HTML5MediaWrapper implements MediaWrapper<MediaBase>, MediaEventHandler {
	protected MediaBase mediaBase;
	protected String uniqId = null;
	protected final EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();
	protected HTML5MediaAvailableOptions availableOptions = new HTML5MediaAvailableOptions();
	protected boolean ready = false;
	protected Map<MediaEventTypes, HandlerRegistration> handlerRegistrations = new HashMap<MediaEventTypes, HandlerRegistration>();
	protected HTML5MediaExecutor mediaExecutor;
	
	public HTML5MediaWrapper(Media media) {		
		setMediaBaseAndPreload(media.getMedia());
		registerEvents();
	}

	public void setMediaExecutor(HTML5MediaExecutor mediaExecutor) {
		this.mediaExecutor = mediaExecutor;
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
		setMediaBaseAndPreload(mediaBase);				
	}

	private void setMediaBaseAndPreload(MediaBase mediaBase) {
		this.mediaBase = mediaBase;
		mediaBase.setPreload(MediaElement.PRELOAD_METADATA);
	}

	private void registerEvents() {
		handlerRegistrations.put(MediaEventTypes.ON_DURATION_CHANGE, eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), this, this, new CurrentPageScope()));
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
		if (MediaEventTypes.ON_DURATION_CHANGE.equals(eventType)) {
			ready = true;
			removeHandler(eventType);
		}
	}

	protected void removeHandler(MediaEventTypes mediaEventType) {
		Set<Entry<MediaEventTypes, HandlerRegistration>> handleRegistrationsEntrySet = handlerRegistrations.entrySet();
		for (Entry<MediaEventTypes, HandlerRegistration> handlerRegistrationEntry : handleRegistrationsEntrySet) {
			MediaEventTypes entryMediaEventType = handlerRegistrationEntry.getKey();
			if (entryMediaEventType.equals(mediaEventType)) {
				HandlerRegistration handlerRegistration = handlerRegistrationEntry.getValue();
				handlerRegistration.removeHandler();
			}			
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
