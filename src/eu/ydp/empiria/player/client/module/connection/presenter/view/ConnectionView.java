package eu.ydp.empiria.player.client.module.connection.presenter.view;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveHandler;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartHandler;
import eu.ydp.empiria.player.client.module.view.HasDimensions;

public interface ConnectionView extends IsWidget ,HasDimensions{

	public void addFirstColumnItem(ConnectionItem item);

	public void addSecondColumnItem(ConnectionItem item);

	public void addElementToMainView(Widget widget);

	public void addConnectionMoveHandler(ConnectionMoveHandler handler);

	public void addConnectionMoveEndHandler(ConnectionMoveEndHandler handler);

	public void addConnectionMoveStartHandler(ConnectionMoveStartHandler handler);

	public Element getElement();

}