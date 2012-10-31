package eu.ydp.empiria.player.client.module.connection.view.event;

import com.google.gwt.dom.client.NativeEvent;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;

public class ConnectionMoveStartEvent  extends ConnectionMoveEvent {
	private final ConnectionItem item;

	public ConnectionMoveStartEvent(double xPos, double yPos, NativeEvent event, ConnectionItem item) {
		super(xPos, yPos, event);
		this.item = item;
	}

//	public ConnectionItem getItem() {
//		return item;
//	}
}
