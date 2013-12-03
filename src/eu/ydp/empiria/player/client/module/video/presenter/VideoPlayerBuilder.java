package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.module.video.hack.VideoPlayerPauseOnPageChangeHandler;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPlayerBuilder {

	private final VideoPlayerFactory videoPlayerFactory;
	private final VideoBean videoBean;
	private final EventsBus eventsBus;

	@Inject
	public VideoPlayerBuilder(VideoPlayerFactory videoPlayerFactory, @ModuleScoped VideoBean videoBean, EventsBus eventsBus) {
		this.videoPlayerFactory = videoPlayerFactory;
		this.videoBean = videoBean;
		this.eventsBus = eventsBus;
	}

	public VideoPlayer buildVideoPlayer() {
		VideoPlayer videoPlayer = videoPlayerFactory.create(videoBean);

		VideoPlayerControl control = videoPlayer.getControl();
		HandlerRegistration handlerRegistration = registerPauseOnPageChange(control);
		deregisterHandlerOnDetach(videoPlayer, handlerRegistration);

		return videoPlayer;
	}

	private void deregisterHandlerOnDetach(VideoPlayer videoPlayer, final HandlerRegistration handlerRegistration) {
		videoPlayer.addAttachHandler(new Handler() {
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				if (!event.isAttached()) {
					handlerRegistration.removeHandler();
				}
			}
		});
	}

	private HandlerRegistration registerPauseOnPageChange(final VideoPlayerControl control) {
		final Type<PlayerEventHandler, PlayerEventTypes> pageChangeEvent = PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE);
		final VideoPlayerPauseOnPageChangeHandler pauseHandler = new VideoPlayerPauseOnPageChangeHandler(control);

		return eventsBus.addHandler(pageChangeEvent, pauseHandler);
	}

}
