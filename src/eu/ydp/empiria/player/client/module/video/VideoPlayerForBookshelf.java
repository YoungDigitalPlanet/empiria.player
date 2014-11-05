package eu.ydp.empiria.player.client.module.video;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoConnectorListener;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.event.factory.Command;

public class VideoPlayerForBookshelf implements FullscreenVideoConnectorListener {

	private final VideoView view;
	private final ExternalFullscreenVideoConnector externalFullscreenVideoConnector;

	private double currentTimeMillipercent;

	@Inject
	public VideoPlayerForBookshelf(@Assisted VideoView videoView, ExternalFullscreenVideoConnector externalFullscreenVideoConnector) {
		this.view = videoView;
		this.externalFullscreenVideoConnector = externalFullscreenVideoConnector;
		this.currentTimeMillipercent = 0;
	}

	public void init() {
			externalFullscreenVideoConnector.addConnectorListener(view.getFirstPlayerId(), this);
			view.preparePlayDelegationToJS(new Command() {
				@Override
				public void execute(NativeEvent nativeEvent) {
					List<String> sources = Lists.newArrayList(view.getVideoSource());
					externalFullscreenVideoConnector.openFullscreen(view.getFirstPlayerId(), sources, currentTimeMillipercent);
				}
			});
	}

	@Override
	public void onFullscreenClosed(String id, double currentTimeMillipercent) {
		if (id.equals(view.getFirstPlayerId())) {
			this.currentTimeMillipercent = currentTimeMillipercent;
		}
	}

}
