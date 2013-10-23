package eu.ydp.empiria.player.client.module.connection.presenter;

import static eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType.*;

import javax.inject.Inject;

import com.google.gwt.dom.client.NativeEvent;

import eu.ydp.empiria.player.client.module.UserAgentCheckerWrapper;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.HasConnectionMoveHandlers;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;

public class ConnectionModuleViewImplHandlers implements HasConnectionMoveHandlers {

	private ConnectionModuleViewImpl view;

	@Inject
	private UserAgentCheckerWrapper userAgent;

	@Inject
	private ConnectionsBetweenItemsFinder connectionsFinder;

	@Inject
	private EventsBus eventsBus;

	@Inject
	private PositionHelper positionHelper;

	@Inject
	private ConnectionSurfacesManager surfacesManager;

	public void setView(ConnectionModuleViewImpl view) {
		this.view = view;
	}

	@Override
	public void onConnectionStart(ConnectionMoveStartEvent event) {
		if (view.isLocked())
			return;

		ConnectionItem item = connectionsFinder.findConnectionItemForEventOnWidget(event.getNativeEvent(), view, view.getConnectionItems());

		if (item == null) {
			NativeEvent event1 = event.getNativeEvent();
			Point clickPoint = getClicktPoint(event1);
			ConnectionPairEntry<String, String> pointOnPath = surfacesManager.findPointOnPath(view.getConnectedSurfaces(), clickPoint);

			if (pointOnPath != null) {
				surfacesManager.removeSurfaceFromParent(view.getConnectedSurfaces(), pointOnPath);
				view.disconnect(pointOnPath.getSource(), pointOnPath.getTarget(), true);
				event1.preventDefault();
			}
		} else {
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
			event.getNativeEvent().preventDefault();
			view.getSurfaceForLineDrawing(item, NORMAL);
			view.startDrawLine(item);
			item.setConnected(true, NORMAL);

			ConnectionItem source = view.getConnectionItemPair().getSource();
			if (source != null && !item.equals(source)) {
				view.resetIfNotConnected(source.getBean().getIdentifier());
			}

			view.getConnectionItemPair().setSource(item);
		}
	}

	@Override
	public void onConnectionMoveEnd(ConnectionMoveEndEvent event) {
		ConnectionItem connectionStartItem = view.getConnectionItemPair().getSource();

		ConnectionItem connectionEndItem = null;
		for (ConnectionItem item : view.getConnectionItems().getConnectionItems(connectionStartItem)) {
			if (isTouchEndOnItem(event.getX(), event.getY(), item)) {
				connectionEndItem = item;
				break;
			}
		}

		if (connectionEndItem != null) {
			view.drawLineFromSource(connectionEndItem.getRelativeX(), connectionEndItem.getRelativeY());
			view.connectItems(connectionStartItem, connectionEndItem, NORMAL, true);
			view.resetTouchConnections();
		}

		if (connectionStartItem != null && !view.isLocked() && connectionEndItem == null) {
			boolean mayConnect = view.getConnectionItemPair().getTarget() != null && !view.getConnectionItemPair().getTarget().equals(connectionStartItem);

			if (mayConnect) {
				view.connect(connectionStartItem, view.getConnectionItemPair().getTarget(), NORMAL, true);
				view.resetTouchConnections();
			} else {
				view.getConnectionItemPair().setTarget(connectionStartItem);
			}
		}
		view.clearSurface(connectionStartItem);
	}

	@Override
	public void onConnectionMoveCancel() {
		ConnectionItem connectionStartItem = view.getConnectionItemPair().getSource();
		if (connectionStartItem != null) {
			view.resetIfNotConnected(connectionStartItem.getBean().getIdentifier());
			view.resetTouchConnections();
			view.clearSurface(connectionStartItem);
		}
	}

	@Override
	public void onConnectionMove(ConnectionMoveEvent event) {
		ConnectionItem source = view.getConnectionItemPair().getSource();
		if (!view.isLocked() && view.getStartPositions().containsKey(source)) {
			event.preventDefault();
			if (wasMoved(event)) {
				updatePointPosition(event);
				view.drawLineFromSource((int) event.getX(), (int) event.getY());
			}
		}
	}

	private Point getClicktPoint(NativeEvent event) {
		return positionHelper.getPoint(event, view.getConnectionViewElement());
	}

	private boolean wasMoved(ConnectionMoveEvent event) {
		return Math.abs(view.getLastPoint().getSource() - event.getX()) > approximation()
				|| Math.abs(view.getLastPoint().getTarget() - event.getY()) > approximation();
	}

	private void updatePointPosition(ConnectionMoveEvent event) {
		view.getLastPoint().setSource(event.getX());
		view.getLastPoint().setTarget(event.getY());
	}

	private int approximation() {
		return userAgent.isStackAndroidBrowser() ? 15 : 5;
	}

	private boolean isTouchEndOnItem(double x, double y, ConnectionItem item) {
		return item.getOffsetLeft() <= x && x <= item.getOffsetLeft() + item.getWidth() && item.getOffsetTop() <= y
				&& y <= item.getOffsetTop() + item.getHeight();
	}

}