package eu.ydp.empiria.player.client.module.video.presenter;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.event.EventImpl.Type;

public class VideoPlayerAttachHandler implements Handler {
	private static final Type<PlayerEventHandler, PlayerEventTypes> PAGE_CHANGE_EVENT_TYPE = PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE);

	private final EventsBus eventsBus;
	private final VideoPlayer videoPlayer;

	private Optional<HandlerRegistration> handlerRegistration = Optional.absent();

	@Inject
	public VideoPlayerAttachHandler(@Assisted VideoPlayer videoPlayer, EventsBus eventsBus) {
		this.eventsBus = eventsBus;
		this.videoPlayer = videoPlayer;
	}

	@Override
	public void onAttachOrDetach(AttachEvent event) {
		if (event.isAttached()) {
			final HandlerRegistration pauseHandlerRegistration = registerPauseHandlerOnPageChange();
			this.handlerRegistration = Optional.of(pauseHandlerRegistration);
		} else {
			clearHandler();
		}
	}

	private void clearHandler() {
		if (handlerRegistration.isPresent()) {
			this.handlerRegistration.get().removeHandler();
			this.handlerRegistration = Optional.absent();
		}
	}

	private HandlerRegistration registerPauseHandlerOnPageChange() {
		final VideoPlayerControl videoPlayerControl = videoPlayer.getControl();
		final AutoPauseOnPageChangeHandler pauseHandler = new AutoPauseOnPageChangeHandler(videoPlayerControl);

		return eventsBus.addHandler(PAGE_CHANGE_EVENT_TYPE, pauseHandler);
	}
}