package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface VideoView extends IsWidget {

	void createView();

	void attachVideoPlayer(VideoPlayer videoPlayer);
}
