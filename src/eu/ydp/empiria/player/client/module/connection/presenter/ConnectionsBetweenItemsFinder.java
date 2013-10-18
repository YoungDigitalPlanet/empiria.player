package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.NativeEventWrapper;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;

public class ConnectionsBetweenItemsFinder {

	@Inject
	private PositionHelper positionHelper;

	@Inject
	private NativeEventWrapper nativeEvent;
	
	public ConnectionItem findConnectionItemForEventOnWidget(NativeEvent event, IsWidget widget, ConnectionItems connectionItems) {
		Point clickPoint = positionHelper.getPoint(event, widget);

		ConnectionItem item = findItemOnPosition(clickPoint, connectionItems);

		if (item != null) {
			nativeEvent.preventDefault(event);
		}
		
		return item;
	}

	private ConnectionItem findItemOnPosition(Point clickPoint, ConnectionItems connectionItems) {
		for (ConnectionItem item : connectionItems.getAllItems()) {
			if (isItemOnPosition(item, clickPoint.getX(), clickPoint.getY())) {
				return item;
			}
		}
		return null;
	}

	private boolean isItemOnPosition(ConnectionItem item, int xPos, int yPos) {
		return xPos >= leftBorder(item) && xPos <= rightBorder(item) && yPos >= topBorder(item) && yPos <= bottomBorder(item);
	}

	private int topBorder(ConnectionItem item) {
		return item.getOffsetTop();
	}

	private int leftBorder(ConnectionItem item) {
		return item.getOffsetLeft();
	}

	private int bottomBorder(ConnectionItem item) {
		return topBorder(item) + item.getHeight();
	}

	private int rightBorder(ConnectionItem item) {
		return leftBorder(item) + item.getWidth();
	}

}
