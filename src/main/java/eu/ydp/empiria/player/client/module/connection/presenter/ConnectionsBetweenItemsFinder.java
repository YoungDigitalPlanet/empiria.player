/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
