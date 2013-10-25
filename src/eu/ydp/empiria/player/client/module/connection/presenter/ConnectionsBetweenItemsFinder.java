package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.NativeEventWrapper;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;

public class ConnectionsBetweenItemsFinder {
	private final class PredicateImplementation implements Predicate<ConnectionItem> {
		private final Point clickPoint;

		private PredicateImplementation(Point clickPoint) {
			this.clickPoint = clickPoint;
		}

		@Override
		public boolean apply(ConnectionItem item) {
			return isItemOnPosition(item, clickPoint);
		}
	}

	@Inject
	private PositionHelper positionHelper;

	@Inject
	private NativeEventWrapper nativeEvent;

	public ConnectionItem findConnectionItemForEventOnWidget(NativeEvent event, IsWidget widget, ConnectionItems connectionItems) {
		Point clickPoint = positionHelper.getPoint(event, widget);

		Optional<ConnectionItem> item = findItemOnPosition(clickPoint, connectionItems);

		if (item.isPresent()) {
			nativeEvent.preventDefault(event);
			return item.get();
		} else {
			return null;
		}
	}

	private Optional<ConnectionItem> findItemOnPosition(final Point clickPoint, ConnectionItems connectionItems) {
		return Iterables.tryFind(connectionItems.getAllItems(), new PredicateImplementation(clickPoint));
	}

	private boolean isItemOnPosition(ConnectionItem item, Point point) {
		final int xPos = point.getX();
		final int yPos = point.getY();
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
