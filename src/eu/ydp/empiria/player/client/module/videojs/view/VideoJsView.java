package eu.ydp.empiria.player.client.module.videojs.view;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.videojs.model.VideoJsModel;

public interface VideoJsView extends IsWidget {

	void createView(VideoJsModel videoJsModel);

}
