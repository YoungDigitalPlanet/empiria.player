package eu.ydp.empiria.player.client.module.slideshow.view.slide;

import com.google.gwt.user.client.ui.IsWidget;

public interface SlideView extends IsWidget {
	void setSlideTitle(String title);

	void clearSlideTitle();

	void setNarration(String narration);

	void clearNarration();

	void setImage(String src);
}
