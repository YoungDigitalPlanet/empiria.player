package eu.ydp.empiria.player.client.module.img.picture.player.lightbox;

import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.lightbox2.LightBox2;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.magnific.popup.MagnificPopup;
import javax.inject.Inject;

public class LightBoxProvider {

	@Inject
	private MagnificPopup magnificPopup;
	@Inject
	private LightBox2 lightBox2;

	public LightBox getFullscreen(String mode) {

		switch (mode) {
		case "magnific":
			return magnificPopup;
		default:
			return lightBox2;
		}
	}
}
