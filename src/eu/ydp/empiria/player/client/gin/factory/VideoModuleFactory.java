package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerAttachHandler;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;

public interface VideoModuleFactory {
	VideoPlayer createVideoPlayer(VideoElementWrapper videoElement);
	VideoPlayerAttachHandler createAttachHandlerForRegisteringPauseEvent(VideoPlayer videoPlayer);
}
