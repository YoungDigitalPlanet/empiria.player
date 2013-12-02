package eu.ydp.empiria.player.client.module.video.hack;

import eu.ydp.empiria.player.client.module.video.view.VideoPlayerNative;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;

public final class VideoPlayerPageChangeHandler implements PlayerEventHandler {
	private final VideoPlayerNative nativePlayer;

	public VideoPlayerPageChangeHandler(VideoPlayerNative nativePlayer) {
		this.nativePlayer = nativePlayer;
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		nativePlayer.pause();
	}
}