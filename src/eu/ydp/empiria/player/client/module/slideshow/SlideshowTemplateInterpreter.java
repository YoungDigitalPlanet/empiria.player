package eu.ydp.empiria.player.client.module.slideshow;

import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowPlayerBean;

public class SlideshowTemplateInterpreter {

	public boolean isPagerTemplateActivate(SlideshowPlayerBean slideshowPlayer) {
		if (isAnyTemplateActivate(slideshowPlayer)) {
			String pager = slideshowPlayer.getTemplate().getSlideshowPager();
			return pager != null;
		}

		return false;
	}

	private boolean isAnyTemplateActivate(SlideshowPlayerBean slideshowPlayer) {
		if (slideshowPlayer.getTemplate() != null) {
			return true;
		}

		return false;
	}
}
