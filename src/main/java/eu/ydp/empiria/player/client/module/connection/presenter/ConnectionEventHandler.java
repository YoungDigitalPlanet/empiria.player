package eu.ydp.empiria.player.client.module.connection.presenter;

import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventTypes;
import eu.ydp.gwtutil.client.event.AbstractEventHandler;

public class ConnectionEventHandler extends AbstractEventHandler<PairConnectEventHandler, PairConnectEventTypes, PairConnectEvent> {

    protected void fireConnectEvent(PairConnectEventTypes type, String source, String target, boolean userAction) {
        PairConnectEvent event = new PairConnectEvent(type, source, target, userAction);
        fireEvent(event);
    }

    @Override
    protected void dispatchEvent(PairConnectEventHandler handler, PairConnectEvent event) {
        handler.onConnectionEvent(event);
    }

    public void addPairConnectEventHandler(PairConnectEventHandler handler) {
        for (PairConnectEventTypes type : PairConnectEventTypes.values()) {
            addHandler(handler, PairConnectEvent.getType(type));
        }
    }

}
