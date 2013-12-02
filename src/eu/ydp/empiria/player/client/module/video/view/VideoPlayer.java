package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.video.hack.PageChangePauser;
import eu.ydp.empiria.player.client.module.video.hack.ReAttachVideoPlayerForIOSChecker;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;

public class VideoPlayer extends Widget {

	private final VideoPlayerNative nativePlayer;
	private final VideoElementWrapper videoElementWrapper;
	private final PageChangePauser pauser;

	private final boolean alwaysReAttach;
	private boolean isLoaded = false;

	@Inject
	public VideoPlayer(@Assisted VideoElementWrapper videoElementWrapper, VideoPlayerNative nativePlayer, ReAttachVideoPlayerForIOSChecker hackChecker,
			PageChangePauser pauser) {
		this.nativePlayer = nativePlayer;
		this.videoElementWrapper = videoElementWrapper;
		this.alwaysReAttach = hackChecker.isNeeded();
		this.pauser = pauser;
		setElement(Document.get().createDivElement());
	}

	@Override
	protected void onLoad() {
		if (shouldReattach()) {
			getElement().appendChild(videoElementWrapper.asNode());

			initializeNativePlayer();

			pauser.registerPauseOnPageChange(nativePlayer);

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

	@Override
	protected void onUnload() {
		nativePlayer.unload();

		if (alwaysReAttach) {
			nativePlayer.disposeCurrentPlayer();
		}
	}
}
