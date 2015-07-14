package eu.ydp.empiria.player.client.controller.multiview;

import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;

class PageViewLoadedHandler implements PlayerEventHandler {
    private IMultiPageController multiPageController;

    public PageViewLoadedHandler(IMultiPageController multiPageController) {
        this.multiPageController = multiPageController;
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        multiPageController.detachAttachPanels();
    }
}
