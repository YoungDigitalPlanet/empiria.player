package eu.ydp.empiria.player.client.module.connection.item;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.presenter.view.AbstractConnectionView;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchTypes;

public class ConnectionItemImpl implements TouchHandler, ConnectionItem {
	private final ConnectionItemView view;
	private AbstractConnectionView connectionView;
	private final PairChoiceBean bean;

	@Inject
	public ConnectionItemImpl(ConnectionModuleFactory itemViewFactory, @Assisted InlineBodyGeneratorSocket socket, @Assisted PairChoiceBean bean) {
		this.bean = bean;
		view = itemViewFactory.getConnectionItemView(bean, socket);
		view.addTouchHandler(this, TouchEvent.getType(TouchTypes.TOUCH_START));
		view.addTouchHandler(this, TouchEvent.getType(TouchTypes.TOUCH_END));
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#getBean()
	 */
	@Override
	public PairChoiceBean getBean() {
		return bean;
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#setConnectionView(eu.ydp.empiria.player.client.module.connection.presenter.view.AbstractConnectionView)
	 */
	@Override
	public void setConnectionView(AbstractConnectionView connectionView) {
		this.connectionView = connectionView;
	}

	@Override
	public Widget asWidget() {
		return view;
	}

	private void touchStart(NativeEvent event) {
		event.preventDefault();
		view.selected();
		connectionView.onTouchStart(event, this);
	}

	private void touchEnd(NativeEvent event) {
		event.preventDefault();
		view.reset();
		connectionView.onTouchEnd(event, this);
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#reset()
	 */
	@Override
	public void reset(){
		view.reset();
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#setConnected(boolean)
	 */
	@Override
	public void setConnected(boolean connected){
		view.setSelected(connected);
	}
	@Override
	public void onTouchEvent(TouchEvent event) {
		switch (event.getType()) {
		case TOUCH_START:
			touchStart(event.getNativeEvent());
			break;
		case TOUCH_END:
			touchEnd(event.getNativeEvent());
			break;
		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#getRelativeX()
	 */
	@Override
	public int getRelativeX() {
		return getOffsetLeft() + getWidth() / 2;
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#getRelativeY()
	 */
	@Override
	public int getRelativeY() {
		return getOffsetTop() + getHeight() / 2;
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#getOffsetLeft()
	 */
	@Override
	public int getOffsetLeft() {
		return view.getSelectionElement().getElement().getOffsetLeft();
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#getOffsetTop()
	 */
	@Override
	public int getOffsetTop() {
		return view.getSelectionElement().getElement().getOffsetTop();
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#getWidth()
	 */
	@Override
	public int getWidth() {
		return view.getSelectionElement().getOffsetWidth();
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#getHeight()
	 */
	@Override
	public int getHeight() {
		return view.getSelectionElement().getOffsetHeight();
	}

}
