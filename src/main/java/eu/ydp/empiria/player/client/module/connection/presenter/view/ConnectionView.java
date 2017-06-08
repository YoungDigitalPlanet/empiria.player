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
}
