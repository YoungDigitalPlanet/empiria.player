package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventTypes;

public class ConnectionViewResizeHandler implements ResizeHandler {
    private ConnectionEventHandler connectionModuleViewImpl;

    public void setConnectionModuleViewImpl(ConnectionEventHandler connectionModuleViewImpl) {
        this.connectionModuleViewImpl = connectionModuleViewImpl;
    }

    @Override
    public void onResize(ResizeEvent event) {
        if (connectionModuleViewImpl != null) {
            connectionModuleViewImpl.fireConnectEvent(PairConnectEventTypes.REPAINT_VIEW, "", "", true);
        }
    }
}
