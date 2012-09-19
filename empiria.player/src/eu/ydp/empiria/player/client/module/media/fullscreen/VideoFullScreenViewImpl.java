package eu.ydp.empiria.player.client.module.media.fullscreen;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class VideoFullScreenViewImpl extends Composite implements VideoFullScreenView {

	private static VideoFullScreenViewUiBinder uiBinder = GWT.create(VideoFullScreenViewUiBinder.class);

	interface VideoFullScreenViewUiBinder extends UiBinder<Widget, VideoFullScreenViewImpl> {
	}


	@UiField
	protected FlowPanel container;

	@UiField
	protected FlowPanel controls;


	public VideoFullScreenViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenView#getContainer()
	 */
	@Override
	public FlowPanel getContainer() {
		return container;
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenView#getControls()
	 */
	@Override
	public FlowPanel getControls() {
		return controls;
	}


}
