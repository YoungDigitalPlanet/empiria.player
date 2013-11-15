package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.video.view.VideoElementWrapper;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;

public interface VideoModuleFactory {
	VideoPlayer createVideoPlayer(VideoElementWrapper videoElement);
}
