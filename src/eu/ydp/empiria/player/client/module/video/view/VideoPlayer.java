package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.module.video.hack.ReAttachVideoPlayerForIOSChecker;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;

public class VideoPlayer extends Widget {

	private final VideoPlayerNative nativePlayer;
	private final VideoElementWrapper videoElementWrapper;

	private final boolean alwaysReAttach;
	private boolean isLoaded = false;

	@Inject
	public VideoPlayer(@Assisted VideoElementWrapper videoElementWrapper, VideoPlayerNative nativePlayer, ReAttachVideoPlayerForIOSChecker hackChecker) {
		this.nativePlayer = nativePlayer;
		this.videoElementWrapper = videoElementWrapper;
		this.alwaysReAttach = hackChecker.isNeeded();
		setElement(Document.get().createDivElement());
	}

	@Override
	protected void onLoad() {
		if (shouldReattach()) {
			getElement().appendChild(videoElementWrapper.asNode());

			initializeNativePlayer();

			isLoaded = true;
		}
	}

	private void initializeNativePlayer() {
		String playerId = videoElementWrapper.getId();
		nativePlayer.initPlayer(playerId);
	}

	private boolean shouldReattach() {
		return !isLoaded || alwaysReAttach;
	}

	public VideoPlayerControl getController() {
		return nativePlayer;
	}

	@Override
	protected void onUnload() {
		nativePlayer.unload();

		if (alwaysReAttach) {
			nativePlayer.disposeCurrentPlayer();
		}
	}
}
