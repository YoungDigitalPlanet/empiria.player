package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.gwtutil.client.event.factory.Command;

@Singleton
public class TouchReservationCommand implements Command {
    @Inject
    private TouchController touchController;

    @Override
    public void execute(NativeEvent event) {
        touchController.setTouchReservation(true);
    }
}
