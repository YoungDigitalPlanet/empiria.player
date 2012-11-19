package eu.ydp.empiria.player.client.module.connection.presenter.view;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.TouchRecognitionFactory;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchTypes;
import eu.ydp.empiria.player.client.util.position.PositionHelper;

public class ConnectionViewVertical extends AbstractConnectionView {

	private static ConnectionViewUiBinder uiBinder = GWT.create(ConnectionViewUiBinder.class);

	interface ConnectionViewUiBinder extends UiBinder<Widget, ConnectionViewVertical> {
	}

	@UiField
	protected FlowPanel leftColumn;

	@UiField
	protected FlowPanel centerColumn;

	@UiField
	protected FlowPanel rightColumn;

	@UiField
	protected FlowPanel view;

	Map<String, String> errorStyles = null, correctStyles = null;

	protected HasTouchHandlers touchRecognition;

	@Inject
	public ConnectionViewVertical(EventsBus eventsBus, PositionHelper positionHelper, TouchRecognitionFactory touchRecognitionFactory) {
		super(eventsBus, positionHelper, touchRecognitionFactory);
	}

	@Override
	public void createAndBindUi() {
		initWidget(uiBinder.createAndBindUi(this));
		touchRecognition = touchRecognitionFactory.getTouchRecognition(this, true, true);
		touchRecognition.addTouchHandlers(this, TouchEvent.getTypes(TouchTypes.TOUCH_START, TouchTypes.TOUCH_END, TouchTypes.TOUCH_MOVE));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView
	 * #addFirstColumnItem(eu.ydp.empiria.player.client.module.connection.item.
	 * ConnectionItem)
	 */

	@Override
	public void addFirstColumnItem(ConnectionItem item) {
		leftColumn.add(item);
		// item.setConnectionView(this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView
	 * #addSecondColumnItem(eu.ydp.empiria.player.client.module.connection.item.
	 * ConnectionItem)
	 */

	@Override
	public void addSecondColumnItem(ConnectionItem item) {
		rightColumn.add(item);
		// item.setConnectionView(this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView
	 * #addElementToMainView(com.google.gwt.user.client.ui.Widget)
	 */

	@Override
	public void addElementToMainView(Widget widget) {
		view.insert(widget, 0);
	}

	@Override
	public void onTouchStart(NativeEvent event) {
		event.stopPropagation();
		ConnectionMoveStartEvent connectionEvent = new ConnectionMoveStartEvent(getPositionX(event), getPositionY(event), event, null);
		callOnMoveStartHandlers(connectionEvent);

	}

	@Override
	public void onTouchEnd(NativeEvent event) {
		ConnectionMoveEndEvent connectionMoveEndEvent = new ConnectionMoveEndEvent(getPositionX(event), getPositionY(event), event);
		callOnMoveEndHandlers(connectionMoveEndEvent);
	}

	@Override
	public FlowPanel getView() {
		return view;
	}
}
