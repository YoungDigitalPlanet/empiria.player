package eu.ydp.empiria.player.client.module.slideshow.view.buttons;

import com.google.gwt.user.client.ui.IsWidget;

public interface SlideshowButtonsView extends IsWidget {
	void setEnabledNextButton(boolean enabled);

	void setEnabledPreviousButton(boolean enabled);

	void setPlayButtonDown(boolean down);

	void setPresenter(Presenter presenter);

	boolean isPlayButtonDown();

	interface Presenter {
		void onNextClick();

		void onPreviousClick();

		void onPlayClick();

		void onStopClick();
	}
}
