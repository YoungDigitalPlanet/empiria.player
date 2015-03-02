package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.dictionary.external.DictionaryMimeSourceProvider;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class ExplanationEntrySoundController {

	@Inject
	private DictionaryMimeSourceProvider dictionaryMimeSourceProvider;
	@Inject
	private EventsBus eventsBus;
	@Inject
	private MediaWrapperController mediaWrapperController;

	public void playEntrySound(Entry entry) {
		String fileName = entry.getEntrySound();

		Map<String, String> sourcesWithTypes = dictionaryMimeSourceProvider.getSourcesWithTypes(fileName);

		BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, false, true);

		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, new CallbackReceiver<MediaWrapper<Widget>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> mediaWrapper) {
				mediaWrapperController.stopAndPlay(mediaWrapper);
			}
		}));
	}

}
