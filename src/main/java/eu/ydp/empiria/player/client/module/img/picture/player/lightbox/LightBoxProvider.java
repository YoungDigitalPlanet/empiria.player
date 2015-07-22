package eu.ydp.empiria.player.client.module.img.picture.player.lightbox;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.lightbox2.LightBox2;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.magnific.popup.MagnificPopup;

import javax.inject.Inject;

public class LightBoxProvider {

    @Inject
    private Provider<MagnificPopup> magnificPopupProvider;
    @Inject
    private Provider<LightBox2> lightBox2Provider;

    public LightBox getFullscreen(String mode) {

        switch (mode) {
            case "magnific":
                return magnificPopupProvider.get();
            default:
                return lightBox2Provider.get();
        }
    }
}
