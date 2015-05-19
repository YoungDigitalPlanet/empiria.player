package eu.ydp.empiria.player.client.lightbox;

import eu.ydp.empiria.player.client.lightbox.lightbox2.LightBox2;
import eu.ydp.empiria.player.client.lightbox.magnific.popup.MagnificPopup;
import javax.inject.Inject;

public class FullScreenModeProvider {

	@Inject
	private MagnificPopup magnificPopup;
	@Inject
	private LightBox2 lightBox2;

	public FullScreen getFullscreen(String mode) {

		switch (mode) {
		case "magnific":
			return magnificPopup;
		default:
			return lightBox2;
		}
	}
}
