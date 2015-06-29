package eu.ydp.empiria.player.client.module.video.presenter;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;

public final class AutoPauseOnPageChangeHandler implements PlayerEventHandler {
	private final VideoPlayerControl playerControl;

	public AutoPauseOnPageChangeHandler(VideoPlayerControl playerControl) {
		this.playerControl = playerControl;
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		playerControl.pause();
	}
}
