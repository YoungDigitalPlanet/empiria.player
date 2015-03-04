package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.*;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.video.*;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import java.util.List;

public class VideoPlayer extends Widget {

	private final VideoPlayerNative nativePlayer;
	private final VideoElementWrapper videoElementWrapper;
	private final UserAgentUtil userAgentUtil;
	private final DivElement divElement;
	private ElementScaler elementScaler;

	@Inject
	public VideoPlayer(@Assisted VideoElementWrapper videoElementWrapper, VideoPlayerNative nativePlayer, UserAgentUtil userAgentUtil) {
		this.nativePlayer = nativePlayer;
		this.videoElementWrapper = videoElementWrapper;
		this.userAgentUtil = userAgentUtil;
		divElement = Document.get().createDivElement();
		setElement(divElement);
	}

	@Override
	protected void onLoad() {
		divElement.appendChild(videoElementWrapper.asNode());

		initializeNativePlayer();

		enablePlayerScaling();
	}

	private void enablePlayerScaling() {
		final Element wrappedElement = divElement.getFirstChildElement();
		final int playerWidth = nativePlayer.getWidth();
		elementScaler = new ElementScaler(wrappedElement);

		elementScaler.setRatio();
		elementScaler.setMaxWidth(playerWidth);
		nativePlayer.addFullscreenListener(new VideoFullscreenListener() {
			@Override
			public void onEnterFullscreen() {
				elementScaler.clearRatio();
				elementScaler.clearMaxWidth();
			}

			@Override
			public void onExitFullscreen() {
				elementScaler.setRatio();
				elementScaler.setMaxWidth(playerWidth);
			}
		});
	}

	private void initializeNativePlayer() {
		String playerId = videoElementWrapper.getId();
		nativePlayer.initPlayer(playerId);

		if (userAgentUtil.isAndroidBrowser() && userAgentUtil.isAIR()) {
			nativePlayer.disablePointerEvents();
		}

		nativePlayer.addVideoEndListener(new VideoEndListener() {
			@Override
			public void onVideoEnd() {
				nativePlayer.stop();
			}
		});
	}

	public VideoPlayerControl getControl() {
		return nativePlayer;
	}

	@Override
	protected void onUnload() {
		nativePlayer.disposeCurrentPlayer();
	}

	public String getId() {
		return videoElementWrapper.getId();
	}

	public List<String> getSources() {
		return videoElementWrapper.getSources();
	}
}
