package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.model.VideoModel;

public class VideoViewImpl extends Composite implements VideoView {

	private static VideoViewImplUiBinder uiBinder = GWT.create(VideoViewImplUiBinder.class);
	@UiField(provided = true)
	VideoPlayer videoJsPlayer;
	@Inject
	private VideoPlayerFactory videoPlayerFactory;

	interface VideoViewImplUiBinder extends UiBinder<Widget, VideoViewImpl> {
	}

	@Override
	public void createView(VideoModel videoJsModel) {
		videoJsPlayer = videoPlayerFactory.create(videoJsModel);
		initWidget(uiBinder.createAndBindUi(this));

		// videoPlayer.create();
	}
}
