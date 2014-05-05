package eu.ydp.empiria.player.client.media;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.player.HideNativeMediaControlsManager;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.MimeUtil;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class SoundPlayer implements MediaEventHandler, CallbackRecevier<MediaWrapper<Widget>> {

	private MediaWrapper<Widget> mediaWrapper;
	private boolean isPlaying;
	private String filePath;

	@Inject
	private EventsBus eventsBus;

	@Inject
	private MediaWrapperCreator mediaWrapperCreator;

	@Inject
	private MimeUtil mimeUtil;

	@Inject
	private HideNativeMediaControlsManager nativeMediaControlsManager;

	private MediaEventHandler externalHandler;

	public void play(String filePath) {
		if (filePath == null) {
			return;
		}

		stop();

		if (!filePath.equals(this.filePath)) {
			this.filePath = filePath;
			createWrapperFromFileAndPlay(this.filePath);
		} else {
			play();
		}
	}

	public void stop() {
		if (isPlaying) {
			fireStopEvent();
		}

		isPlaying = false;
	}

	private void createWrapperFromFileAndPlay(String filePath) {
		Map<String, String> sourcesWithTypes = Maps.newHashMap();
		String mimeType = mimeUtil.getMimeTypeFromExtension(filePath);
		sourcesWithTypes.put(filePath, mimeType);

		mediaWrapperCreator.createMediaWrapper(filePath, sourcesWithTypes, this);
	}

	public void addExternalHandler(MediaEventHandler handler) {
		this.externalHandler = handler;
	}

	private void addWrapperEventsHandlers() {
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), mediaWrapper, this);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), mediaWrapper, this);
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		MediaWrapper<?> eventMediaWrapper = event.getMediaWrapper();

		if (eventMediaWrapper == mediaWrapper) {
			switch (event.getType()) {
			case ON_END:
				isPlaying = false;
				propagateEventOnExternalHandler(event);
				break;
			case ON_PLAY:
				isPlaying = true;
				break;
			default:
				break;
			}
		}
	}

	private void fireStopEvent() {
		fireEvent(MediaEventTypes.STOP);
	}

	private void firePlayEvent() {
		fireEvent(MediaEventTypes.PLAY);
	}

	private void fireEvent(MediaEventTypes eventType) {
		eventsBus.fireEventFromSource(new MediaEvent(eventType, mediaWrapper), mediaWrapper);
	}

	private void play() {
		isPlaying = true;
		firePlayEvent();
	}

	private void propagateEventOnExternalHandler(MediaEvent event) {
		if (externalHandler != null) {
			externalHandler.onMediaEvent(event);
		}
	}

	@Override
	public void setCallbackReturnObject(MediaWrapper<Widget> o) {
		mediaWrapper = o;
		nativeMediaControlsManager.addToDocumentAndHideControls(mediaWrapper);
		addWrapperEventsHandlers();
		play();
	}
}
