package eu.ydp.empiria.player.client.controller.feedback.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.SingleFeedbackSoundPlayerFactory;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
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
	protected Map<String, MediaWrapper<Widget>> wrappers = new HashMap<String, MediaWrapper<Widget>>();
	protected Map<String, SingleFeedbackSoundPlayer> feedbackPlayers = new HashMap<String, SingleFeedbackSoundPlayer>();

	protected class MediaWrapperHandler implements CallbackReceiver<MediaWrapper<Widget>> {

		protected String wrappersSourcesKey;

		@Override
		public void setCallbackReturnObject(MediaWrapper<Widget> mediaWrapper) {

			wrappers.put(wrappersSourcesKey, mediaWrapper);
			stopAndPlaySound(wrappersSourcesKey);

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
		String sourcesKey = getWrappersSourcesKey(sourcesWithTypes.keySet());
		this.play(sourcesKey, sourcesWithTypes);
	}

	protected void play(String sourcesKey, Map<String, String> sourcesWithTypes) {
		if (wrappers.containsKey(sourcesKey)) {
			stopAndPlaySound(sourcesKey);
		} else {
			createMediaWrapperAndStopAndPlaySound(sourcesKey, sourcesWithTypes);
		}
	}

	protected SingleFeedbackSoundPlayer addSingleFeedbackSoundPlayerIfNotPresent(String sourcesKey) {
		SingleFeedbackSoundPlayer onStopPlayHandler = feedbackPlayers.get(sourcesKey);
		if (onStopPlayHandler == null) {
			onStopPlayHandler = createAndStoreSingleFeedbackSoundPlayer(sourcesKey);
		}
		return onStopPlayHandler;
	}

	protected SingleFeedbackSoundPlayer createAndStoreSingleFeedbackSoundPlayer(String sourcesKey) {
		MediaWrapper<?> mediaWrapper = wrappers.get(sourcesKey);
		SingleFeedbackSoundPlayer onStopPlayHandler = feedbackPlayerFactory.getSingleFeedbackSoundPlayer(mediaWrapper);
		feedbackPlayers.put(sourcesKey, onStopPlayHandler);
		return onStopPlayHandler;
	}

	protected void stopAndPlaySound(final String sourcesKey) {
		SingleFeedbackSoundPlayer player = addSingleFeedbackSoundPlayerIfNotPresent(sourcesKey);
		player.play();
	}

	protected void createMediaWrapperAndStopAndPlaySound(String sourcesKey, Map<String, String> sourcesWithTypes) {
		MediaWrapperHandler callbackHandler = new MediaWrapperHandler();
		callbackHandler.setWrappersSourcesKey(sourcesKey);
		mediaWrapperCreator.createMediaWrapper(sourcesKey, sourcesWithTypes, callbackHandler);
	}

	// Klucz po ktorym przeszukiwany jest cache.
	protected String getWrappersSourcesKey(Iterable<String> sources) {
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
