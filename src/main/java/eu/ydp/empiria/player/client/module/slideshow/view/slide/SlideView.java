package eu.ydp.empiria.player.client.module.slideshow.view.slide;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface SlideView extends IsWidget {
    void setSlideTitle(Widget title);

    void setNarration(Widget narration);

    void setImage(String src);

    void clearTitle();

    void clearNarration();
}
