package eu.ydp.empiria.player.client.module.video;

import com.google.common.collect.Lists;
import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.event.factory.Command;

import java.util.List;

public class VideoPlayerForBookshelfOnAndroid {

	private final ExternalFullscreenVideoConnector externalFullscreenVideoConnector;
	private final String playerId;
	private final String source;

	@Inject
	public VideoPlayerForBookshelfOnAndroid(@Assisted VideoPlayer videoPlayer, ExternalFullscreenVideoConnector externalFullscreenVideoConnector) {
		this.source = videoPlayer.getSource();
		this.playerId = videoPlayer.getId();
		this.externalFullscreenVideoConnector = externalFullscreenVideoConnector;
	}

	public void init(VideoView view) {
		view.preparePlayDelegationToJS(new Command() {
			@Override
			public void execute(NativeEvent nativeEvent) {
				List<String> sources = Lists.newArrayList(source);
				externalFullscreenVideoConnector.openFullscreen(playerId, sources, 0);
			}
		});
	}
}
