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
    private final Column column;

    @Inject
    public ConnectionItemImpl(ConnectionModuleFactory itemViewFactory, @Assisted InlineBodyGeneratorSocket socket, @Assisted PairChoiceBean bean,
                              @Assisted Column column) {
        this.bean = bean;
        this.column = column;
        view = column == Column.LEFT ? itemViewFactory.getConnectionItemViewLeft(bean, socket) : itemViewFactory.getConnectionItemViewRight(bean, socket);
    }

    @Override
    public Column getColumn() {
        return column;
    }

    @Override
    public PairChoiceBean getBean() {
        return bean;
    }

    @Override
    public Widget asWidget() {
        return view;
    }

    @Override
    public void reset() {
        view.reset();
    }

    @Override
    public void setConnected(boolean connected, MultiplePairModuleConnectType connectType) {
        view.setSelected(connected, connectType);
    }

    @Override
    public int getRelativeX() {
        return getOffsetLeft() + getWidth() / 2;
    }

    @Override
    public int getRelativeY() {
        return getOffsetTop() + getHeight() / 2;
    }

    @Override
    public int getOffsetLeft() {
        return view.getSelectionElement().getElement().getOffsetLeft();
    }

    @Override
    public int getOffsetTop() {
        return view.getSelectionElement().getElement().getOffsetTop();
    }

    @Override
    public int getWidth() {
        return view.getSelectionElement().getOffsetWidth();
    }

    @Override
    public int getHeight() {
        return view.getSelectionElement().getOffsetHeight();
    }

}
