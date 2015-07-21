package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.Panel;
import eu.ydp.empiria.player.client.view.page.PageContentView;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;

public interface PageContentFactory {
    PageContentView getPageContentView(Panel parentPanel);
    PageViewSocket getPageViewSocket(PageContentView view);
}
