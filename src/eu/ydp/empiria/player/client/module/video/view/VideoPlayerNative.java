package eu.ydp.empiria.player.client.module.video.view;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;

public interface VideoPlayerNative extends VideoPlayerControl {

	void unload();

	void initPlayer(String playerId);
}
