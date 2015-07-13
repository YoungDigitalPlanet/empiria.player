package eu.ydp.empiria.player.client.module.slideshow.view.player;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface SlideshowPlayerView extends IsWidget {
    void setTitle(Widget title);

    void addPager(Widget pager);
}
