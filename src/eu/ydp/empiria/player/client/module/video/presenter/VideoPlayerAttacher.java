package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.module.video.hack.VideoPlayerPauseOnPageChangeHandler;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPlayerAttacher {

	private final VideoPlayerFactory videoPlayerFactory;
	private final VideoBean videoBean;
	private final EventsBus eventsBus;

	@Inject
	public VideoPlayerAttacher(VideoPlayerFactory videoPlayerFactory, @ModuleScoped VideoBean videoBean, EventsBus eventsBus) {
		this.videoPlayerFactory = videoPlayerFactory;
		this.videoBean = videoBean;
		this.eventsBus = eventsBus;
	}

	public void attachNewToView(VideoView view) {
		VideoPlayer videoPlayer = videoPlayerFactory.create(videoBean);

		VideoPlayerControl control = videoPlayer.getControl();
		registerPauseOnPageChange(control);

		view.attachVideoPlayer(videoPlayer);
	}

	private void registerPauseOnPageChange(final VideoPlayerControl control) {
		final Type<PlayerEventHandler, PlayerEventTypes> pageChangeEvent = PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE);
		final VideoPlayerPauseOnPageChangeHandler pauseHandler = new VideoPlayerPauseOnPageChangeHandler(control);

		eventsBus.addHandler(pageChangeEvent, pauseHandler);
	}

}
