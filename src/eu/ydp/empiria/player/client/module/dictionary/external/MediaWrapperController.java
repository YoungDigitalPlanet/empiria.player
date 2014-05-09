package eu.ydp.empiria.player.client.module.dictionary.external;

import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.*;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.player.HideNativeMediaControlsManager;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class MediaWrapperController {
	@Inject
	private EventsBus eventsBus;
	@Inject
	private HideNativeMediaControlsManager hideNativeMediaControlsManager;

	public void addMediaWrapperControls(MediaWrapper<Widget> mediaWrapper) {
		hideNativeMediaControlsManager.addToDocumentAndHideControls(mediaWrapper);
	}

	public void play(MediaWrapper<Widget> mediaWrapper) {
		fireEventFromSource(STOP, mediaWrapper);
		fireEventFromSource(PLAY, mediaWrapper);
	}

	public void stop(MediaWrapper<Widget> mediaWrapper) {
		fireEventFromSource(PAUSE, mediaWrapper);
	}

	private void fireEventFromSource(MediaEventTypes eventType, MediaWrapper<Widget> mediaWrapper) {
		eventsBus.fireEventFromSource(new MediaEvent(eventType, mediaWrapper), mediaWrapper);
	}

}
