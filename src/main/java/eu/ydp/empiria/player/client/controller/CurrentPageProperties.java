package eu.ydp.empiria.player.client.controller;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;

public class CurrentPageProperties {

    private final PageControllerCache pageControllerCache;
    private final Page page;

    @Inject
    public CurrentPageProperties(PageControllerCache pageControllerCache, Page page) {
        this.pageControllerCache = pageControllerCache;
        this.page = page;

    }

    public boolean hasInteractiveModules() {
        int currentPageNumber = page.getCurrentPageNumber();
        PageController currentPageController = pageControllerCache.getElement(currentPageNumber);
        return currentPageController.hasInteractiveModules();
    }
}
