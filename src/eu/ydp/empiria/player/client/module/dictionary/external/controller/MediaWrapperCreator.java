package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.dictionary.external.MimeSourceProvider;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class MediaWrapperCreator {

	@Inject
	private MimeSourceProvider mimeSourceProvider;

	@Inject
	private EventsBus eventsBus;

	public void create(String filePath, CallbackRecevier<MediaWrapper<Widget>> callback) {
		Map<String, String> sourcesWithTypes = mimeSourceProvider.getSourcesWithTypes(filePath);

		BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, false, true);
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callback));
	}
}
