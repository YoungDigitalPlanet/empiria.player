package eu.ydp.empiria.player.client.module.connection.presenter.view;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveCancelHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartHandler;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public interface ConnectionView extends IsWidget, HasDimensions {

	void addFirstColumnItem(ConnectionItem item);

	void addSecondColumnItem(ConnectionItem item);

	void addElementToMainView(IsWidget widget);

	void addConnectionMoveHandler(ConnectionMoveHandler handler);

	void addConnectionMoveEndHandler(ConnectionMoveEndHandler handler);

	void addConnectionMoveStartHandler(ConnectionMoveStartHandler handler);

	void addConnectionMoveCancelHandler(ConnectionMoveCancelHandler handler);

	void setDrawFollowTouch(boolean followTouch);

	Element getElement();

	void enableTestSubmittedMode();

	void disableTestSubmittedMode();

}