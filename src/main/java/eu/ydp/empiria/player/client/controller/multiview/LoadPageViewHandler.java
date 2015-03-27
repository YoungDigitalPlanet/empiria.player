package eu.ydp.empiria.player.client.controller.multiview;

import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;

class LoadPageViewHandler implements PlayerEventHandler {
	private IMultiPageController multiPageController;

	public LoadPageViewHandler(IMultiPageController multiPageController) {
		this.multiPageController = multiPageController;
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		int pageNumber = 0;
		if (event.getValue() != null) {
			pageNumber = (Integer) event.getValue();
		}
		multiPageController.setVisiblePage(pageNumber);
	}
}
