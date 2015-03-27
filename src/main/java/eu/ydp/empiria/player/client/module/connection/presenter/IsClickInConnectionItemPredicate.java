package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.base.Predicate;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.util.position.Point;

public final class IsClickInConnectionItemPredicate implements Predicate<ConnectionItem> {
	private final Point clickPoint;

	public IsClickInConnectionItemPredicate(Point clickPoint) {
		this.clickPoint = clickPoint;
	}

	@Override
	public boolean apply(ConnectionItem item) {
		return isItemOnPosition(item, clickPoint);
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