package eu.ydp.empiria.player.client.module.connection.view.event;

import com.google.gwt.dom.client.NativeEvent;

public class ConnectionMoveEndEvent extends ConnectionMoveEvent {
    public ConnectionMoveEndEvent(double xPos, double yPos, NativeEvent event) {
        super(xPos, yPos, event);
    }
}
