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

package eu.ydp.empiria.player.client.module.ordering.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItem;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemStyles;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItems;

import java.util.List;

public class OrderInteractionViewImpl implements OrderInteractionView {

    @Inject
    private OrderInteractionViewItems viewItems;

    @Inject
    private OrderInteractionViewWidget viewWidget;

    @Inject
    private OrderInteractionViewItemStyles interactionViewItemStyles;

    @Override
    public void createItem(OrderingItem orderingItem, XMLContent xmlContent, InlineBodyGeneratorSocket bodyGenerator) {
        Widget widgetItem = bodyGenerator.generateInlineBody(xmlContent.getValue());
        addItem(orderingItem, widgetItem);
    }

    private void addItem(OrderingItem orderingItem, Widget widgetItem) {
        OrderInteractionViewItem viewItem = viewItems.addItem(orderingItem.getId(), widgetItem);
        viewWidget.add(viewItem);
    }

    @Override
    public void setChildrenOrder(List<String> childOrder) {
        List<IsWidget> itemsInOrder = viewItems.getItemsInOrder(childOrder);
        putItemsOnView(itemsInOrder);
    }

    private void putItemsOnView(List<IsWidget> itemsInOrder) {
        viewWidget.putItemsOnView(itemsInOrder);
    }

    @Override
    public void setChildStyles(OrderingItem item) {
        interactionViewItemStyles.applyStylesOnWidget(item, viewItems.getItem(item.getId()));
    }

    @Override
    public void setOrientation(OrderInteractionOrientation orientation) {
        viewWidget.setOrientation(orientation);
    }

    @Override
    public Widget asWidget() {
        return viewWidget.asWidget();
    }

    @Override
    public String getMainPanelUniqueCssClass() {
        return viewWidget.getMainPanelUniqueCssClass();
    }
}
