package eu.ydp.empiria.player.client.module.slideshow.view.player;

import com.google.gwt.user.client.ui.*;

public interface SlideshowPlayerView extends IsWidget {
	void setTitle(String title);

	void addPager(Widget pager);
}
