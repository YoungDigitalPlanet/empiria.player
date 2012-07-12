package eu.ydp.empiria.player.client.module.media.button;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

/**
 *
 * przycisk playPause
 *
 * @author plelakowski
 *
 */
public class PlayPauseMediaButton extends AbstractMediaButton<PlayPauseMediaButton> {
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	public PlayPauseMediaButton() {
		super("qp-media-play-pause");
	}

	@Override
	protected void onClick() {
		if (!isActive()) {
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY, getMediaWrapper()), getMediaWrapper());
		} else {
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PAUSE, getMediaWrapper()), getMediaWrapper());
		}
	}

	@Override
	public void init() {
		super.init();
		AbstractMediaEventHandler handler = new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				if (event.getType() == MediaEventTypes.ON_PLAY) {
					setActive(true);
				}else{
					setActive(false);
				}
				changeStyleForClick();
			}
		};
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), getMediaWrapper(), handler);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), getMediaWrapper(), handler);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_STOP), getMediaWrapper(), handler);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), getMediaWrapper(), handler);
	}

	@Override
	public boolean isSupported() {
		return getMediaAvailableOptions().isPlaySupported() && getMediaAvailableOptions().isPauseSupported();
	}

	@Override
	public PlayPauseMediaButton getNewInstance() {
		return new PlayPauseMediaButton();
	}

}
