package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.dictionary.external.MediaWrapperController;
import eu.ydp.empiria.player.client.module.dictionary.external.MimeSourceProvider;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class ExplanationEntrySoundController {

	@Inject
	private MimeSourceProvider mimeSourceProvider;
	@Inject
	private EventsBus eventsBus;
	@Inject
	private MediaWrapperController mediaWrapperController;

	public void playEntrySound(String fileName) {
		Map<String, String> sourcesWithTypes = mimeSourceProvider.getSourcesWithTypes(fileName);

		BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, false, true);

		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, new CallbackRecevier<MediaWrapper<Widget>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> mediaWrapper) {
				mediaWrapperController.addMediaWrapperControls(mediaWrapper);
				mediaWrapperController.play(mediaWrapper);
			}
		}));
	}

}
