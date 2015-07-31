package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.media.fullscreen.VideoControlHideTimer;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenView;

public interface MediaFactory {
    VideoControlHideTimer getVideoControlHideTimer(VideoFullScreenView view);
}
