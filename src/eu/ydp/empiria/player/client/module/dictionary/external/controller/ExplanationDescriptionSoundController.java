package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.*;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.feedback.player.HideNativeMediaControlsManager;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class ExplanationDescriptionSoundController {

	private final ExplanationView explanationView;
	private final EventsBus eventsBus;
	private final MediaWrapperController mediaWrapperController;
	private final HideNativeMediaControlsManager hideNativeMediaControlsManager;

	private final DictionaryMediaWrapperCreator mediaWrapperCreator;
	private MediaWrapper<Widget> mediaWrapper;
	private boolean playing;
	private final Provider<CurrentPageScope> currentPageScopeProvider;

	@Inject
	public ExplanationDescriptionSoundController(@Assisted ExplanationView explanationView, EventsBus eventsBus, MediaWrapperController mediaWrapperController,
			Provider<CurrentPageScope> currentPageScopeProvider, DictionaryMediaWrapperCreator mediaWrapperCreator,
			HideNativeMediaControlsManager hideNativeMediaControlsManager) {
		this.explanationView = explanationView;
		this.eventsBus = eventsBus;
		this.mediaWrapperController = mediaWrapperController;
		this.currentPageScopeProvider = currentPageScopeProvider;
		this.mediaWrapperCreator = mediaWrapperCreator;
		this.hideNativeMediaControlsManager = hideNativeMediaControlsManager;
	}

	private void playDescriptionSound(String filePath) {
		mediaWrapperCreator.create(filePath, new CallbackRecevier<MediaWrapper<Widget>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> mw) {
				onMediaWrapperCallback(mw);
			}

		});
	}

	private void onMediaWrapperCallback(MediaWrapper<Widget> mw) {
		mediaWrapper = mw;
		explanationView.setPlayingButtonStyle();
		AbstractMediaEventHandler handler = createDescriptionSoundMediaHandler();
		addMediaHandlers(handler);
		hideNativeMediaControlsManager.addToDocumentAndHideControls(mediaWrapper);
		playing = true;
		mediaWrapperController.stopAndPlay(mediaWrapper);
	}

	private AbstractMediaEventHandler createDescriptionSoundMediaHandler() {
		return new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				if (!MediaEventTypes.ON_PLAY.equals(event.getType())) {
					explanationView.setStopButtonStyle();
					playing = false;
				}
			}
		};
	}

	public void playOrStopDescriptionSound(Entry entry) {
		if (playing) {
			stop();
		} else {
			String fileName = entry.getEntryExampleSound();
			playDescrSound(fileName);
		}
	}

	private void playDescrSound(String fileName) {
		if (fileName != null && !fileName.isEmpty()) {
			playDescriptionSound(fileName);
		}
	}

	public void stop() {
		playing = false;
		explanationView.setStopButtonStyle();
		mediaWrapperController.stop(mediaWrapper);
	}

	private void addMediaHandlers(AbstractMediaEventHandler handler) {
		addMediaHandler(ON_PAUSE, handler);
		addMediaHandler(ON_END, handler);
		addMediaHandler(ON_STOP, handler);
		addMediaHandler(ON_PLAY, handler);
	}

	private void addMediaHandler(MediaEventTypes type, MediaEventHandler handler) {
		eventsBus.addHandlerToSource(MediaEvent.getType(type), mediaWrapper, handler, currentPageScopeProvider.get());
	}
}
