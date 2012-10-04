package eu.ydp.empiria.player.client.view.player;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.view.page.PageContentView;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;
import eu.ydp.empiria.player.client.view.page.PageViewSocketImpl;
import eu.ydp.gwtutil.client.collections.KeyValue;

public class PageViewCache extends AbstractElementCache<KeyValue<PageViewSocket, PageContentView>> {
	protected final MultiPageController multiPageView = PlayerGinjector.INSTANCE.getMultiPage();
	@Override
	protected KeyValue<PageViewSocket, PageContentView> getElement(int index) {
		PageContentView view = new PageContentView(multiPageView.getViewForPage(index));
		return new KeyValue<PageViewSocket, PageContentView>(new PageViewSocketImpl(view), view);
	};
}
