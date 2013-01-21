package eu.ydp.empiria.player.client.controller.feedback.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.gwtutil.client.StringUtils;

public class FeedbackSoundPlayer {

	@Inject
	protected EventsBus eventsBus;

	@Inject
	protected MediaWrapperCreator mediaWrapperCreator;

	// Cache dla wrapperow - do odegrania danego pliku bedzie uzywany zawsze ten
	// sam wrapper.
	protected Map<String, MediaWrapper<?>> wrappers = new HashMap<String, MediaWrapper<?>>();
	protected Map<MediaWrapper<?>, MediaEventHandler> onStopHandlers = new HashMap<MediaWrapper<?>, MediaEventHandler>();

	protected class MediaWrapperHandler implements CallbackRecevier {

		protected String wrappersSourcesKey;

		@Override
		public void setCallbackReturnObject(Object object) {
			if (object instanceof MediaWrapper<?>) {
				MediaWrapper<?> mediaWrapper = (MediaWrapper<?>) object;
				wrappers.put(wrappersSourcesKey, mediaWrapper);
				stopAndPlaySound(mediaWrapper);
			}
		}

		public void setWrappersSourcesKey(String sourcesKey) {
			this.wrappersSourcesKey = sourcesKey;
		}

	}

	protected void firePlayEvent(final MediaWrapper<?> mediaWrapper) {
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY, mediaWrapper), mediaWrapper);
	}

	protected void fireStopEvent(final MediaWrapper<?> mediaWrapper) {
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.STOP, mediaWrapper), mediaWrapper);
	}

	// Jesli mamy tylko zrodla plikow.
	public void play(List<String> sources) {
		Map<String, String> sourcesWithTypes = new HashMap<String, String>();
		for (String source : sources) {
			sourcesWithTypes.put(source, getMimeType(source));
		}

		this.play(getWrappersSourcesKey(sources), sourcesWithTypes);
	}

	// Jesli mamy zrodla plikow oraz ich MIME.
	public void play(Map<String, String> sourcesWithTypes) {
		String sourcesKey = getWrappersSourcesKey(new ArrayList<String>(sourcesWithTypes.keySet()));

		this.play(sourcesKey, sourcesWithTypes);
	}

	protected void play(String sourcesKey, Map<String, String> sourcesWithTypes) {
		if (wrappers.containsKey(sourcesKey)) {
			stopAndPlaySound(wrappers.get(sourcesKey));
		} else {
			createMediaWrapper(sourcesKey, sourcesWithTypes);
		}
	}

	protected boolean addHandlerForStopIfNotPresent(final MediaWrapper<?> mediaWrapper) {
		boolean isPresent = onStopHandlers.containsKey(mediaWrapper);
		if (!isPresent) {
			MediaEventHandler onStopPlayHandler = createOnStopMediaHandler(mediaWrapper);
			eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_STOP), mediaWrapper, onStopPlayHandler);
			onStopHandlers.put(mediaWrapper, onStopPlayHandler);
		}
		return isPresent;
	}

	protected MediaEventHandler createOnStopMediaHandler(final MediaWrapper<?> mediaWrapper) {
		MediaEventHandler onStopPlayHandler = new MediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				firePlayEvent(mediaWrapper);
			}
		};
		return onStopPlayHandler;
	}

	protected void stopAndPlaySound(final MediaWrapper<?> mediaWrapper) {
		boolean firstTimePlay = addHandlerForStopIfNotPresent(mediaWrapper);
		if(firstTimePlay){
			firePlayEvent(mediaWrapper);
		}else{
			fireStopEvent(mediaWrapper);
		}
	}

	protected void createMediaWrapper(String sourcesKey, Map<String, String> sourcesWithTypes) {
		MediaWrapperHandler callbackHandler = new MediaWrapperHandler();
		callbackHandler.setWrappersSourcesKey(sourcesKey);
		mediaWrapperCreator.createMediaWrapper(sourcesKey, sourcesWithTypes, callbackHandler);
	}

	// Klucz po ktorym przeszukiwany jest cache.
	protected String getWrappersSourcesKey(List<String> sources) {
		return Joiner.on(",").join(sources);
	}

	protected String getMimeType(String url) {
		String mimeType = StringUtils.EMPTY_STRING;
		String fileType = url.substring(url.length() - 3);

		if ("mp3".equals(fileType)) {
			mimeType = "audio/mp4";
		} else if ("ogg".equals(fileType) || "ogv".equals(fileType)) {
			mimeType = "audio/ogg";
		}
		return mimeType;
	}
}
