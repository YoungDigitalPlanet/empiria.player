package eu.ydp.empiria.player.client.module.media.button;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

/**
 * Przycisk mute
 *
 * @author plelakowski
 *
 */
public class MuteMediaButton extends AbstractMediaButton<MuteMediaButton> {
	public MuteMediaButton() {
		super("qp-media-mute");
	}
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	@Override
	protected void onClick() {
		super.onClick();
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.MUTE,getMediaWrapper()), getMediaWrapper());
	}

	@Override
	public void init() {
		super.init();
		AbstractMediaEventHandler eventHandler = new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				if (event.getMediaWrapper().getVolume() == 0) {
					// onClick();
				}
			}
		};
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_VOLUME_CHANGE), getMediaWrapper(), eventHandler);
	}

	@Override
	public MuteMediaButton getNewInstance() {
		return new MuteMediaButton();
	}

	@Override
	public boolean isSupported() {
		return getMediaAvailableOptions().isMuteSupported();
	}
}
