package eu.ydp.empiria.player.client.module.video.hack;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;

public final class VideoPlayerPauseOnPageChangeHandler implements PlayerEventHandler {
	private final VideoPlayerControl playerControl;

	public VideoPlayerPauseOnPageChangeHandler(VideoPlayerControl playerControl) {
		this.playerControl = playerControl;
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		playerControl.pause();
	}
}