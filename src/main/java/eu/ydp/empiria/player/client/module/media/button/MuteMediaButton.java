package eu.ydp.empiria.player.client.module.media.button;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

/**
 * Przycisk mute
 * 
 * @author plelakowski
 * 
 */
public class MuteMediaButton extends AbstractMediaButton<MuteMediaButton> {
	protected final static StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants(); // NOPMD

	public MuteMediaButton() {
		super(styleNames.QP_MEDIA_MUTE());
	}

	protected EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();

	@Override
	protected void onClick() {
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.MUTE, getMediaWrapper()), getMediaWrapper());
	}

	@Override
	public void init() {
		super.init();
		AbstractMediaEventHandler eventHandler = new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				if (event.getMediaWrapper().isMuted()) {
					setActive(true);
				} else {
					setActive(false);
				}
				changeStyleForClick();
			}
		};
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_VOLUME_CHANGE), getMediaWrapper(), eventHandler, new CurrentPageScope());
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
