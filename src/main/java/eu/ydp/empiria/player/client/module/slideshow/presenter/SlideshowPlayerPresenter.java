package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowBean;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;

public class SlideshowPlayerPresenter {

    private final SlideshowPlayerView view;

    @Inject
    public SlideshowPlayerPresenter(SlideshowPlayerView view) {
        this.view = view;
    }

    public void init(SlideshowBean bean, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        Element title = bean.getTitle().getTitleValue().getValue();
        Widget titleView = inlineBodyGeneratorSocket.generateInlineBody(title);
        view.setTitle(titleView);
    }

    public void setPager(Widget pagerWidget) {
        view.addPager(pagerWidget);
    }

    public Widget getView() {
        return view.asWidget();
    }
}
