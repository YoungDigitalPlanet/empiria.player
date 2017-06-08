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

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemsResponseOrderController {

    private final ItemsOrderByAnswersFinder itemsOrderByAnswersFinder;
    private final OrderingItemsDao orderingItemsDao;
    private final OrderInteractionModuleModel model;

    @Inject
    public ItemsResponseOrderController(ItemsOrderByAnswersFinder itemsOrderByAnswersFinder, @ModuleScoped OrderingItemsDao orderingItemsDao,
                                        @ModuleScoped OrderInteractionModuleModel model) {
        this.itemsOrderByAnswersFinder = itemsOrderByAnswersFinder;
        this.orderingItemsDao = orderingItemsDao;
        this.model = model;
    }

    public List<String> getResponseAnswersByItemsOrder(List<String> itemsOrder) {
        List<String> responseAnswers = Lists.newArrayList();
        for (String itemId : itemsOrder) {
            OrderingItem item = orderingItemsDao.getItem(itemId);
            String answerValue = item.getAnswerValue();
            responseAnswers.add(answerValue);
        }

        return responseAnswers;
    }

    public List<String> getCorrectItemsOrderByAnswers(List<String> answers) {
        Collection<OrderingItem> items = orderingItemsDao.getItems();
        List<String> correctItemsOrder = itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(answers, items);
        return correctItemsOrder;
    }

    public void updateResponseWithNewOrder(List<String> newItemsOrder) {
        List<String> responseAnswers = getResponseAnswersByItemsOrder(newItemsOrder);
        model.getResponse().values = new ArrayList<String>(responseAnswers);
    }

    public List<String> getCurrentItemsOrderByAnswers() {
        List<String> currentAnswers = model.getCurrentAnswers();
        return getCorrectItemsOrderByAnswers(currentAnswers);
    }

}
