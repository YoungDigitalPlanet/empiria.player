package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;

public class VideoPlayer extends Widget {

	private final VideoPlayerNative nativePlayer;
	private final VideoElementWrapper videoElementWrapper;

	@Inject
	public VideoPlayer(@Assisted VideoElementWrapper videoElementWrapper, VideoPlayerNative nativePlayer) {
		this.nativePlayer = nativePlayer;
		this.videoElementWrapper = videoElementWrapper;
		setElement(Document.get().createDivElement());
	}

	@Override
	protected void onLoad() {
		getElement().appendChild(videoElementWrapper.asNode());

		initializeNativePlayer();
	}

	private void initializeNativePlayer() {
		String playerId = videoElementWrapper.getId();
		nativePlayer.initPlayer(playerId);
	}

	public VideoPlayerControl getControl() {
		return nativePlayer;
	}

	@Override
	protected void onUnload() {
		nativePlayer.disposeCurrentPlayer();
	}

	public void disablePointerEvents() {
		nativePlayer.disablePointerEvents();
	}

	public String getId() {
		return videoElementWrapper.getId();
	}

	public String getSource() {
		return videoElementWrapper.getSource();
	}
}
