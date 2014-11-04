package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.common.collect.Lists;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoConnectorListener;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

import java.util.List;

public class VideoPresenter implements FullscreenVideoConnectorListener {

	private final VideoView view;
	private final VideoPlayerReattacher reAttachHack;
	private final UserAgentUtil userAgentUtil;
	private final VideoDelegatorToJS playDelegatorToJS;
	private ExternalFullscreenVideoConnector externalFullscreenVideoConnector;
	private final VideoPlayerBuilder videoPlayerBuilder;
	private String source;

	@Inject
	public VideoPresenter(@ModuleScoped VideoView view, @ModuleScoped VideoPlayerBuilder videoPlayerAttacher,
	                      @ModuleScoped VideoPlayerReattacher reAttachHack, UserAgentUtil userAgentUtil,
	                      VideoDelegatorToJS playDelegatorToJS, ExternalFullscreenVideoConnector externalFullscreenVideoConnector) {
		this.view = view;
		this.videoPlayerBuilder = videoPlayerAttacher;
		this.reAttachHack = reAttachHack;
		this.userAgentUtil = userAgentUtil;
		this.playDelegatorToJS = playDelegatorToJS;
		this.externalFullscreenVideoConnector = externalFullscreenVideoConnector;
	}

	public void start() {
		view.createView();

		preparePlayDelegationToJS();

		VideoPlayer videoPlayer = videoPlayerBuilder.build();
		view.attachVideoPlayer(videoPlayer);
		source = videoPlayer.getSource();

		reAttachHack.registerReattachHandlerToView(view);
	}

	private void preparePlayDelegationToJS() {
		if (userAgentUtil.isAndroidBrowser() && userAgentUtil.isAIR()) {
			externalFullscreenVideoConnector.addConnectorListener("id", this);
			view.preparePlayDelegationToJS(new Command() {
				@Override
				public void execute(NativeEvent nativeEvent) {
					List<String> sources = Lists.newArrayList(source);
					externalFullscreenVideoConnector.openFullscreen("id", sources, 0);
				}
			});
		}
	}

	public Widget getView() {
		return view.asWidget();
	}

	@Override
	public void onFullscreenClosed(String id, double currentTimeMillipercent) {
	}
}
