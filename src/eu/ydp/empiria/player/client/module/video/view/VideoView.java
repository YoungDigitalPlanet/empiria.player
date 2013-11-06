package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.video.model.VideoModel;

public interface VideoView extends IsWidget {

	void createView(VideoModel videoJsModel);

}
