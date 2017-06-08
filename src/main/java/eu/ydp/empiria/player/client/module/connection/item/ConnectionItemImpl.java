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
