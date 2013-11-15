package eu.ydp.empiria.player.client.module.video.view;

public interface VideoPlayerNative extends VideoPlayerControl {

	void unload();

	void initPlayer(String playerId);
}
