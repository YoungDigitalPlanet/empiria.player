package eu.ydp.empiria.player.client.module.slideshow.view.buttons;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowButtonsPresenter;

public interface SlideshowButtonsView extends IsWidget {
	void setEnabledNextButton(boolean enabled);

	void setEnabledPreviousButton(boolean enabled);

	void setPlayButtonDown(boolean down);

	void setPresenter(SlideshowButtonsPresenter presenter);

	boolean isPlayButtonDown();
}
