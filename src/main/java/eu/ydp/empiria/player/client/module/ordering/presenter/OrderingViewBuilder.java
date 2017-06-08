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

package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.SimpleOrderChoiceBean;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

public class OrderingViewBuilder {

    private final OrderInteractionView interactionView;
    private final OrderingItemsDao orderingItemsDao;

    @Inject
    public OrderingViewBuilder(@ModuleScoped OrderInteractionView interactionView, @ModuleScoped OrderingItemsDao orderingItemsDao) {
        this.interactionView = interactionView;
        this.orderingItemsDao = orderingItemsDao;
    }

    public void buildView(OrderInteractionBean bean, InlineBodyGeneratorSocket bodyGeneratorSocket) {
        interactionView.setOrientation(bean.getOrientation());
        List<SimpleOrderChoiceBean> itemBeans = bean.getChoiceBeans();

        for (int i = 0; i < itemBeans.size(); i++) {
            SimpleOrderChoiceBean simpleOrderChoiceBean = itemBeans.get(i);
            XMLContent content = getXMLContent(simpleOrderChoiceBean);
            OrderingItem orderingItem = createOrderingItem(String.valueOf(i), simpleOrderChoiceBean);
            interactionView.createItem(orderingItem, content, bodyGeneratorSocket);
            orderingItemsDao.addItem(orderingItem);
        }

        orderingItemsDao.createInitialItemsOrder();
    }

    private OrderingItem createOrderingItem(String itemId, SimpleOrderChoiceBean simpleOrderChoiceBean) {
        String answerValue = simpleOrderChoiceBean.getIdentifier();
        return new OrderingItem(itemId, answerValue);
    }

    private XMLContent getXMLContent(SimpleOrderChoiceBean simpleOrderChoiceBean) {
        return simpleOrderChoiceBean.getContent();
    }
}
