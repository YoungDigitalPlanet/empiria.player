package eu.ydp.empiria.player.client.module.slideshow;

import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowPlayerBean;

public class SlideshowTemplateInterpreter {

    public boolean isPagerTemplateActivate(SlideshowPlayerBean slideshowPlayer) {
        if (slideshowPlayer.hasTemplate()) {
            return slideshowPlayer.getTemplate().hasSlideshowPager();
        }

        return false;
    }
}
