package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.video.hack.ReAttachVideoPlayerForIOSChecker;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;

public class VideoPlayer extends Widget {

	private final VideoPlayerNative nativePlayer;
	private final VideoElementWrapper videoElementWrapper;

	private boolean isLoaded = false;
	private final boolean alwaysReAttach;

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

			String playerId = videoElementWrapper.getId();
			nativePlayer.initPlayer(playerId);

			isLoaded = true;
		}
	}

	private boolean shouldReattach() {
		return !isLoaded || alwaysReAttach;
	}

	@Override
	protected void onUnload() {
		nativePlayer.unload();

		if (alwaysReAttach) {
			nativePlayer.disposeCurrentPlayer();
		}
	}
}
