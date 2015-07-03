package eu.ydp.empiria.player.client.controller.multiview;

import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;

class TouchReservationHandler implements PlayerEventHandler {
    private IMultiPageController multiPageController;

    public TouchReservationHandler(IMultiPageController multiPageController) {
        this.multiPageController = multiPageController;
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        multiPageController.reset();
    }
}
