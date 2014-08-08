package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.feedback.player.HideNativeMediaControlsManager;
import eu.ydp.empiria.player.client.module.dictionary.external.MediaWrapperController;
import eu.ydp.empiria.player.client.module.dictionary.external.MimeSourceProvider;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

import java.util.Map;

public class ExplanationEntrySoundController {

	@Inject
	private MimeSourceProvider mimeSourceProvider;
	@Inject
	private EventsBus eventsBus;
	@Inject
	private MediaWrapperController mediaWrapperController;
	@Inject
	private HideNativeMediaControlsManager hideNativeMediaControlsManager;

	public void playEntrySound(Entry entry) {
		String fileName = entry.getEntrySound();

		Map<String, String> sourcesWithTypes = mimeSourceProvider.getSourcesWithTypes(fileName);

		BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, false, true);

		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, new CallbackRecevier<MediaWrapper<Widget>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> mediaWrapper) {
				hideNativeMediaControlsManager.addToDocumentAndHideControls(mediaWrapper);
				mediaWrapperController.play(mediaWrapper);
			}
		}));
	}

}
