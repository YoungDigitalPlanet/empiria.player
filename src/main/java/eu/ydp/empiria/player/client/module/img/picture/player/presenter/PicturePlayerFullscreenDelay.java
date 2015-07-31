package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBox;

public class PicturePlayerFullscreenDelay {

    public void openImageWithDelay(final LightBox lightBox, final String pictureSrc, final Element title) {
        Timer timer = createFullscreenDelay(lightBox, pictureSrc, title);
        timer.schedule(300);
    }

    private Timer createFullscreenDelay(final LightBox lightBox, final String pictureSrc, final Element title) {
        return new Timer() {
            @Override
            public void run() {
                lightBox.openImage(pictureSrc, title);
            }
        };
    }
}
