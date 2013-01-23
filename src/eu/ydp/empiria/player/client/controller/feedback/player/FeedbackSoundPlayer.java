package eu.ydp.empiria.player.client.controller.feedback.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.SingleFeedbackSoundPlayerFactory;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.gwtutil.client.StringUtils;

public class FeedbackSoundPlayer {

	@Inject
	protected EventsBus eventsBus;

	@Inject
	protected MediaWrapperCreator mediaWrapperCreator;

	@Inject
	protected SingleFeedbackSoundPlayerFactory feedbackPlayerFactory;

	// Cache dla wrapperow - do odegrania danego pliku bedzie uzywany zawsze ten
	// sam wrapper.
	protected Map<String, MediaWrapper<?>> wrappers = new HashMap<String, MediaWrapper<?>>();
	protected Map<MediaWrapper<?>, SingleFeedbackSoundPlayer> onStopHandlers = new HashMap<MediaWrapper<?>, SingleFeedbackSoundPlayer>();

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

	protected SingleFeedbackSoundPlayer addSingleFeedbackSoundPlayerIfNotPresent(final MediaWrapper<?> mediaWrapper) {
		SingleFeedbackSoundPlayer onStopPlayHandler = onStopHandlers.get(mediaWrapper);
		if (onStopPlayHandler == null) {
			onStopPlayHandler = createAndStoreSingleFeedbackSoundPlayer(mediaWrapper);
		}
		return onStopPlayHandler;
	}

	protected SingleFeedbackSoundPlayer createAndStoreSingleFeedbackSoundPlayer(final MediaWrapper<?> mediaWrapper) {
		SingleFeedbackSoundPlayer onStopPlayHandler = feedbackPlayerFactory.getSingleFeedbackSoundPlayer(mediaWrapper);
		onStopHandlers.put(mediaWrapper, onStopPlayHandler);
		return onStopPlayHandler;
	}

	protected void stopAndPlaySound(final MediaWrapper<?> mediaWrapper) {
		SingleFeedbackSoundPlayer player = addSingleFeedbackSoundPlayerIfNotPresent(mediaWrapper);
		player.play();
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
