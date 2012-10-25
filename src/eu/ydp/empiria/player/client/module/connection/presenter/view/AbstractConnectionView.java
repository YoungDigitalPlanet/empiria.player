package eu.ydp.empiria.player.client.module.connection.presenter.view;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartHandler;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public abstract class AbstractConnectionView extends Composite implements TouchMoveHandler, MouseMoveHandler, ConnectionView {
	private final Set<ConnectionMoveHandler> handlers = new HashSet<ConnectionMoveHandler>();
	private final Set<ConnectionMoveEndHandler> endMoveHandlers = new HashSet<ConnectionMoveEndHandler>();
	private final Set<ConnectionMoveStartHandler> startMoveHandlers = new HashSet<ConnectionMoveStartHandler>();
	protected final EventsBus eventsBus;
	private final PositionHelper positionHelper;

	public AbstractConnectionView(EventsBus eventsBus, PositionHelper positionHelper) {
		createAndBindUi();
		this.addDomHandler(this, TouchMoveEvent.getType());
		this.addDomHandler(this, MouseMoveEvent.getType());
			this.eventsBus = eventsBus;
		this.positionHelper = positionHelper;
	}

	@Override
	public void addElementToMainView(Widget widget) {
		getView().add(widget);
	}

	@Override
	public void addConnectionMoveHandler(ConnectionMoveHandler handler) {
		handlers.add(handler);
	}

	@Override
	public void addConnectionMoveEndHandler(ConnectionMoveEndHandler handler) {
		endMoveHandlers.add(handler);
	}

	@Override
	public void addConnectionMoveStartHandler(ConnectionMoveStartHandler handler) {
		startMoveHandlers.add(handler);
	}

	protected void callOnMoveHandlers(ConnectionMoveEvent event) {
		for (ConnectionMoveHandler handler : handlers) {
			handler.onConnectionMove(event);
		}
	}

	protected void callOnMoveEndHandlers(ConnectionMoveEndEvent event) {
		for (ConnectionMoveEndHandler handler : endMoveHandlers) {
			handler.onConnectionMoveEnd(event);
		}
	}

	protected void callOnMoveStartHandlers(ConnectionMoveStartEvent event) {
		for (ConnectionMoveStartHandler handler : startMoveHandlers) {
			handler.onConnectionStart(event);
		}
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		if (getView() != null) {
			if (UserAgentChecker.isStackAndroidBrowser()) {
				event.preventDefault();
			}
			callOnMoveHandlers(new ConnectionMoveEvent(getPositionX(event.getNativeEvent()), getPositionY(event.getNativeEvent()), event.getNativeEvent()));
		}
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		if (getView() != null) {
			callOnMoveHandlers(new ConnectionMoveEvent(getPositionX(event.getNativeEvent()), getPositionY(event.getNativeEvent()), event.getNativeEvent()));
		}
	}

	public void addFirstColumnItems(Collection<ConnectionItem> items) {
		for (ConnectionItem item : items) {
			addFirstColumnItem(item);
		}
	}

	public void addSecondColumnItems(Collection<ConnectionItem> items) {
		for (ConnectionItem item : items) {
			addSecondColumnItem(item);
		}
	}

	protected int getPositionX(NativeEvent event) {
		return positionHelper.getPositionX(event, getView().getElement());
	}

	protected int getPositionY(NativeEvent event) {
		return positionHelper.getPositionY(event, getView().getElement());
	}

	public abstract void createAndBindUi();

	public abstract FlowPanel getView();

	@Override
	public abstract void addFirstColumnItem(ConnectionItem item);

	@Override
	public abstract void addSecondColumnItem(ConnectionItem item);

	public abstract void onTouchStart(NativeEvent event, ConnectionItem item);

	public abstract void onTouchEnd(NativeEvent event, ConnectionItem item);
}
