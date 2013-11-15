package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;

public class VideoPlayer extends Widget {

	private final VideoPlayerNative nativePlayer;
	private VideoElementWrapper videoElementWrapper;

	private boolean isLoaded = false;

	@Inject
	public VideoPlayer(@Assisted VideoElementWrapper videoElementWrapper, VideoPlayerNative nativePlayer) {
		this.nativePlayer = nativePlayer;
		this.videoElementWrapper = videoElementWrapper;
		setElement(Document.get().createDivElement());
	}

	@Override
	protected void onLoad() {
		if (!isLoaded) {
			getElement().appendChild(videoElementWrapper.asNode());

			String playerId = videoElementWrapper.getId();
			nativePlayer.initPlayer(playerId);

			isLoaded = true;
		}
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		nativePlayer.unload();
	}
}
