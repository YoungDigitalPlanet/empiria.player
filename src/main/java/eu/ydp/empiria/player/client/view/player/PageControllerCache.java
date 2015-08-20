package eu.ydp.empiria.player.client.view.player;

import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.PageController;

@Singleton
public class PageControllerCache extends AbstractElementCache<PageController> {

    @Override
    public PageController getElement(Integer index) {
        return getCache().get(index);
    }

}
