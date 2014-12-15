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
	private final ElementScaler elementScaler;

	@Inject
	public VideoPlayer(@Assisted VideoElementWrapper videoElementWrapper, VideoPlayerNative nativePlayer, UserAgentUtil userAgentUtil,
			ElementScaler elementScaler) {
		this.nativePlayer = nativePlayer;
		this.videoElementWrapper = videoElementWrapper;
		this.userAgentUtil = userAgentUtil;
		this.elementScaler = elementScaler;
		divElement = Document.get().createDivElement();
		setElement(divElement);
	}

	@Override
	protected void onLoad() {
		divElement.appendChild(videoElementWrapper.asNode());

		initializeNativePlayer();

		scalePlayer();
	}

	private void scalePlayer() {
		Element wrapperElement = divElement.getFirstChildElement();
		elementScaler.scale(wrapperElement);
	}

	private void initializeNativePlayer() {
		String playerId = videoElementWrapper.getId();
		nativePlayer.initPlayer(playerId);

		if (userAgentUtil.isAndroidBrowser() && userAgentUtil.isAIR()) {
			nativePlayer.disablePointerEvents();
		}
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
