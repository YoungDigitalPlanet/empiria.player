package eu.ydp.empiria.player.client.view.player;

import eu.ydp.empiria.player.client.controller.PageController;

public class PageControllerCache extends AbstractElementCache<PageController> {

	@Override
	public PageController getElement(Integer index) {
		return getCache().get(index);
	}

}
