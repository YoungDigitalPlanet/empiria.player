package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoViewImpl extends Composite implements VideoView {

	private static VideoViewImplUiBinder uiBinder = GWT.create(VideoViewImplUiBinder.class);

	@UiField(provided = true)
	VideoPlayer videoPlayer;
	@Inject
	private VideoPlayerFactory videoPlayerFactory;
	@Inject
	@ModuleScoped
	private VideoBean videoBean;

	interface VideoViewImplUiBinder extends UiBinder<Widget, VideoViewImpl> {
	}

	@Override
	public void createView() {
		videoPlayer = videoPlayerFactory.create(videoBean);
		initWidget(uiBinder.createAndBindUi(this));
	}
}
