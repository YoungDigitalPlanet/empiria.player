package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.NativeEventWrapper;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEvent;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.position.PositionHelper;

public class ConnectionsBetweenItemsFinder {
    @Inject
    private PositionHelper positionHelper;

    @Inject
    private NativeEventWrapper nativeEventWrapper;

    public Optional<ConnectionItem> findConnectionItemForEventOnWidget(ConnectionMoveEvent event, IsWidget widget, ConnectionItems connectionItems) {
        NativeEvent nativeEvent = event.getNativeEvent();
        Point clickPoint = positionHelper.getPoint(nativeEvent, widget);

        Optional<ConnectionItem> item = findItemOnPosition(clickPoint, connectionItems);

        if (item.isPresent()) {
            nativeEventWrapper.preventDefault(nativeEvent);
        }
        return item;

    }

    private Optional<ConnectionItem> findItemOnPosition(final Point clickPoint, ConnectionItems connectionItems) {
        return Iterables.tryFind(connectionItems.getAllItems(), new IsClickInConnectionItemPredicate(clickPoint));
    }

}
