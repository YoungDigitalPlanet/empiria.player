package eu.ydp.empiria.player.client.module.media.button;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import static  eu.ydp.empiria.player.client.util.UserAgentChecker.MobileUserAgent.*;

/**
 * Przycisk mute
 *
 * @author plelakowski
 *
 */
public class MuteMediaButton extends AbstractMediaButton<MuteMediaButton> {
	public MuteMediaButton() {
		super("qp-media-mute",FIREFOX, CHROME);
	}

	@Override
	protected void onClick() {
		super.onClick();
		getMedia().setMuted(clicked);
	}

	@Override
	public void init() {
		getMedia().addBitlessDomHandler(new HTML5MediaEventHandler() {
			@Override
			public void onEvent(HTML5MediaEvent event) {
				if (getMedia().getVolume() == 0) {
					//onClick();
				}
			}
		}, HTML5MediaEvent.getType(HTML5MediaEventsType.volumechange));
	}

	@Override
	public MuteMediaButton getNewInstance() {
		return new MuteMediaButton();
	}
}
