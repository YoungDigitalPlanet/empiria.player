package eu.ydp.empiria.player.client.module.videojs.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.videojs.model.VideoJsModel;

public class VideoJsViewImpl extends Composite implements VideoJsView {

	private static VideoJsViewImplUiBinder uiBinder = GWT.create(VideoJsViewImplUiBinder.class);
	@UiField(provided = true)
	VideoJsPlayer videoJsPlayer;
	@Inject
	private VideoPlayerFactory videoPlayerFactory;

	interface VideoJsViewImplUiBinder extends UiBinder<Widget, VideoJsViewImpl> {
	}

	@Override
	public void createView(VideoJsModel videoJsModel) {
		videoJsPlayer = videoPlayerFactory.create(videoJsModel);
		initWidget(uiBinder.createAndBindUi(this));

		// videoJsPlayer.create();
	}
}
