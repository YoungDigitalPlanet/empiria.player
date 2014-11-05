package eu.ydp.empiria.player.client.module.video.view;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class VideoViewImpl extends Composite implements VideoView {

	private static VideoViewImplUiBinder uiBinder = GWT.create(VideoViewImplUiBinder.class);

	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
	@UiField
	FlowPanel container;

	private boolean pointerEventsEnabled = true;
	private String currentPlayerId = "";
	private String source = "";

	@Override
	public String getFirstPlayerId() {
		return currentPlayerId;
	}

	interface VideoViewImplUiBinder extends UiBinder<Widget, VideoViewImpl> {
	}

	@Override
	public void createView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void preparePlayDelegationToJS(Command command) {
		userInteractionHandlerFactory.createUserClickHandler(command).apply(container);
		pointerEventsEnabled = false;
	}

	@Override
	public void attachVideoPlayer(VideoPlayer videoPlayer) {
		setVideoId(videoPlayer.getId());
		source = videoPlayer.getSource();

		container.clear();
		container.add(videoPlayer);
		if (!pointerEventsEnabled) {
			videoPlayer.disablePointerEvents();
		}
	}

	private void setVideoId(String id) {
		if (currentPlayerId.isEmpty()) {
			currentPlayerId = id;
		}
	}

	@Override
	public String getVideoSource() {
		return source;
	}
}
