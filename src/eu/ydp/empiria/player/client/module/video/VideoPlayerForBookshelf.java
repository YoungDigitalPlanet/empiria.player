package eu.ydp.empiria.player.client.module.video;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.event.factory.Command;

public class VideoPlayerForBookshelf {

	private final VideoView view;
	private final ExternalFullscreenVideoConnector externalFullscreenVideoConnector;

	@Inject
	public VideoPlayerForBookshelf(@Assisted VideoView videoView, ExternalFullscreenVideoConnector externalFullscreenVideoConnector) {
		this.view = videoView;
		this.externalFullscreenVideoConnector = externalFullscreenVideoConnector;
	}

	public void init() {
		view.preparePlayDelegationToJS(new Command() {
			@Override
			public void execute(NativeEvent nativeEvent) {
				List<String> sources = Lists.newArrayList(view.getVideoSource());
				externalFullscreenVideoConnector.openFullscreen(view.getPlayerId(), sources, 0);
			}
		});
	}
}
