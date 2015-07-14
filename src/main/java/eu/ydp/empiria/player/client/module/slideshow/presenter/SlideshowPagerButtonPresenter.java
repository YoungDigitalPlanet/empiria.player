package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.view.pager.button.SlideshowPagerButtonView;
import eu.ydp.gwtutil.client.event.factory.Command;

public class SlideshowPagerButtonPresenter {

    private final SlideshowPagerButtonView view;

    @Inject
    public SlideshowPagerButtonPresenter(SlideshowPagerButtonView view) {
        this.view = view;
    }

    public void setClickCommand(Command clickCommand) {
        view.setOnClickCommand(clickCommand);
    }

    public void activatePagerButton() {
        view.activatePagerButton();
    }

    public void deactivatePagerButton() {
        view.deactivatePagerButton();
    }

    public Widget getView() {
        return view.asWidget();
    }
}
