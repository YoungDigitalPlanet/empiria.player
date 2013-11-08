package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.VideoElement;

public interface VideoPlayerNative extends VideoPlayerControl {

	VideoElement createVideoElement();

	void unload();

	void initPlayer();
}
