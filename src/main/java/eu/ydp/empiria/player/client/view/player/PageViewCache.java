package eu.ydp.empiria.player.client.view.player;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.view.page.PageContentView;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;
import eu.ydp.empiria.player.client.view.page.PageViewSocketImpl;
import eu.ydp.gwtutil.client.collections.KeyValue;

public class PageViewCache extends AbstractElementCache<KeyValue<PageViewSocket, PageContentView>> {
    @Inject
    private MultiPageController multiPageView;

    @Override
    protected KeyValue<PageViewSocket, PageContentView> getElement(Integer index) {
        PageContentView view = new PageContentView(multiPageView.getViewForPage(index));
        return new KeyValue<PageViewSocket, PageContentView>(new PageViewSocketImpl(view), view);
    }
}
