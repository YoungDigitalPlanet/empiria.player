package eu.ydp.empiria.player.client.module.video.view;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;

public interface VideoPlayerNative extends VideoPlayerControl {

	void initPlayer(String playerId);

	void disposeCurrentPlayer();

	public void disablePointerEvents();

}
