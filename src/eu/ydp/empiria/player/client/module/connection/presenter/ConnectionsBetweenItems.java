package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;

public class ConnectionsBetweenItems {

	@Inject
	private PositionHelper positionHelper;
	private final IsWidget viewElement;
	private final ConnectionItems connectionItems;

	@Inject
	public ConnectionsBetweenItems(@Assisted IsWidget view,@Assisted ConnectionItems connectionItems) {
		this.viewElement = view;
		this.connectionItems = connectionItems;
	}

	protected ConnectionItem findConnectionItem(NativeEvent event) {
		Point clickPoint = getClicktPoint(event);

		ConnectionItem item = findItemOnPosition(clickPoint);
		preventDefaultBehaviorIfNotNull(event, item);
		return item;
	}

	protected void preventDefaultBehaviorIfNotNull(NativeEvent event, ConnectionItem item) {
		if (item != null) {
			event.preventDefault(); // disable text selection
		}
	}

	private ConnectionItem findItemOnPosition(Point clickPoint) {
		for (ConnectionItem item : connectionItems.getAllItems()) {
			if (item.isOnPosition(clickPoint.getX(), clickPoint.getY())) {
				return item; //NOPMD
			}
		}
		return null;
	}

	protected Point getClicktPoint(NativeEvent event) {
		return positionHelper.getPoint(event, viewElement);
	}
}
