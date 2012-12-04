package eu.ydp.empiria.player.client.module.connection.item;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;

public class ConnectionItemImpl implements ConnectionItem {
	private final AbstractConnectionItemView view;
	private final PairChoiceBean bean;

	@Inject
	public ConnectionItemImpl(ConnectionModuleFactory itemViewFactory, @Assisted InlineBodyGeneratorSocket socket, @Assisted PairChoiceBean bean,
			@Assisted Column column) {
		this.bean = bean;
		view = column == Column.LEFT ? itemViewFactory.getConnectionItemViewLeft(bean, socket) : itemViewFactory.getConnectionItemViewRight(bean, socket);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#getBean
	 * ()
	 */
	@Override
	public PairChoiceBean getBean() {
		return bean;
	}

	@Override
	public Widget asWidget() {
		return view;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#reset
	 * ()
	 */
	@Override
	public void reset() {
		view.reset();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#
	 * setConnected(boolean)
	 */
	@Override
	public void setConnected(boolean connected,MultiplePairModuleConnectType connectType) {
		view.setSelected(connected,connectType);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#
	 * getRelativeX()
	 */
	@Override
	public int getRelativeX() {
		return getOffsetLeft() + getWidth() / 2;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#
	 * getRelativeY()
	 */
	@Override
	public int getRelativeY() {
		return getOffsetTop() + getHeight() / 2;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#
	 * getOffsetLeft()
	 */
	@Override
	public int getOffsetLeft() {
		return view.getSelectionElement().getElement().getOffsetLeft();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#
	 * getOffsetTop()
	 */
	@Override
	public int getOffsetTop() {
		return view.getSelectionElement().getElement().getOffsetTop();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#getWidth
	 * ()
	 */
	@Override
	public int getWidth() {
		return view.getSelectionElement().getOffsetWidth();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#getHeight
	 * ()
	 */
	@Override
	public int getHeight() {
		return view.getSelectionElement().getOffsetHeight();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.ydp.empiria.player.client.module.connection.item.ConnectionItem#
	 * isOnPosition(int, int)
	 */
	@Override
	public boolean isOnPosition(int xPos, int yPos) {
		return xPos >= getOffsetLeft() && xPos <= getOffsetLeft() + getWidth() && yPos >= getOffsetTop() && yPos <= getOffsetTop() + getHeight();
	}

}
