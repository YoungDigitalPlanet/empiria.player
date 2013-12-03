package eu.ydp.empiria.player.client.module.video.hack;

import javax.inject.Inject;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class PageChangePauseHandlerAdder {

	@Inject
	private EventsBus eventsBus;

	public void registerPauseOnPageChange(final VideoPlayerControl playerController) {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), new VideoPlayerPauseOnPageChangeHandler(playerController));
	}
}
