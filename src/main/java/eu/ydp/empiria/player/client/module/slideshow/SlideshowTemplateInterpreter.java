package eu.ydp.empiria.player.client.module.slideshow;

import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowPlayerBean;

@Singleton
public class SlideshowTemplateInterpreter {

    public boolean isPagerTemplateActivate(SlideshowPlayerBean slideshowPlayer) {
        if (slideshowPlayer.hasTemplate()) {
            return slideshowPlayer.getTemplate().hasSlideshowPager();
        }

        return false;
    }
}
