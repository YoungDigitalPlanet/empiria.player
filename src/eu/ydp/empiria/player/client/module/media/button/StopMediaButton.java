package eu.ydp.empiria.player.client.module.media.button;

import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

/**
 *
 * przycisk stop
 *
 * @author plelakowski
 *
 */
public class StopMediaButton extends AbstractMediaButton<StopMediaButton> {
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	public StopMediaButton() {
		super("qp-media-stop", false);
	}

	@Override
	protected void onClick() {
		changeStyleForClick();
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.STOP,getMediaWrapper()),getMediaWrapper());
	}

	@Override
	public StopMediaButton getNewInstance() {
		return new StopMediaButton();
	}

	@Override
	public boolean isSupported() {
		return getMediaAvailableOptions().isStopSupported();
	}
}
