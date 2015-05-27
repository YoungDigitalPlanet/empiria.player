package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.*;

public class EntryDescriptionSoundController {

	private final ExplanationView explanationView;
	private final EventsBus eventsBus;
	private final MediaWrapperController mediaWrapperController;

	private final DictionaryMediaWrapperCreator mediaWrapperCreator;
	private MediaWrapper<Widget> mediaWrapper;
	private boolean playing;
	private final Provider<CurrentPageScope> currentPageScopeProvider;

	@Inject
	public EntryDescriptionSoundController(@Assisted ExplanationView explanationView, EventsBus eventsBus, MediaWrapperController mediaWrapperController,
										   Provider<CurrentPageScope> currentPageScopeProvider, DictionaryMediaWrapperCreator mediaWrapperCreator) {
		this.explanationView = explanationView;
		this.eventsBus = eventsBus;
		this.mediaWrapperController = mediaWrapperController;
		this.currentPageScopeProvider = currentPageScopeProvider;
		this.mediaWrapperCreator = mediaWrapperCreator;
	}

	private void playEntrySoundFile(String filePath) {
		mediaWrapperCreator.create(filePath, new CallbackReceiver<MediaWrapper<Widget>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> mw) {
				onEntryMediaWrapperCallback(mw);
			}

		});
	}

	private void onEntryMediaWrapperCallback(MediaWrapper<Widget> mw) {
		explanationView.setEntryPlayButtonStyle();
		AbstractMediaEventHandler handler = createEntrySoundMediaHandler();
		playFromMediaWrapper(handler, mw);
	}

	public void playFromMediaWrapper(AbstractMediaEventHandler handler, MediaWrapper<Widget> mw){
		mediaWrapper = mw;
		addMediaHandlers(handler);
		playing = true;
		mediaWrapperController.stopAndPlay(mediaWrapper);
	}

	private AbstractMediaEventHandler createEntrySoundMediaHandler() {
		return new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				if (!MediaEventTypes.ON_PLAY.equals(event.getType())) {
					explanationView.setEntryStopButtonStyle();
					playing = false;
				}
			}
		};
	}

	public void playOrStopEntrySound(Entry entry) {
		if (playing) {
			stop();
		} else {
			String fileName = entry.getEntrySound();
			playEntryLectorSound(fileName);
		}
	}

	private void playEntryLectorSound(String fileName) {
		if (fileName != null && !fileName.isEmpty()) {
			playEntrySoundFile(fileName);
		}
	}

	public void stop() {
		playing = false;
		explanationView.setEntryStopButtonStyle();
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