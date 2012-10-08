package eu.ydp.empiria.player.client.module.media.button;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

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
		super(styleNames.QP_MEDIA_PLAY_PAUSE());
	}

	@Override
	protected void onClick() {
		if (isActive()) {
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PAUSE, getMediaWrapper()), getMediaWrapper());
		} else {
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY, getMediaWrapper()), getMediaWrapper());
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
				} else {
					setActive(false);
				}
				changeStyleForClick();
			}
		};
		CurrentPageScope scope = new CurrentPageScope();
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), getMediaWrapper(), handler, scope);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), getMediaWrapper(), handler, scope);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_STOP), getMediaWrapper(), handler, scope);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), getMediaWrapper(), handler, scope);
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
