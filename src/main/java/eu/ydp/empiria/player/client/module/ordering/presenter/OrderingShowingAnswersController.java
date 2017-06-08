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
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

public class OrderingShowingAnswersController {

    private final OrderingItemsDao orderingItemsDao;
    private final ItemsResponseOrderController itemsResponseOrderController;
    private final OrderInteractionModuleModel model;

    @Inject
    public OrderingShowingAnswersController(@ModuleScoped ItemsResponseOrderController itemsResponseOrderController,
                                            @ModuleScoped OrderingItemsDao orderingItemsDao, @ModuleScoped OrderInteractionModuleModel model) {
        this.orderingItemsDao = orderingItemsDao;
        this.itemsResponseOrderController = itemsResponseOrderController;
        this.model = model;
    }

    public List<String> findNewAnswersOrderToShow(ShowAnswersType mode) {
        List<String> answersOrder;

        if (mode == ShowAnswersType.CORRECT) {
            List<String> correctAnswers = model.getCorrectAnswers();
            answersOrder = itemsResponseOrderController.getCorrectItemsOrderByAnswers(correctAnswers);
        } else {
            List<String> currentAnswers = model.getCurrentAnswers();
            answersOrder = itemsResponseOrderController.getCorrectItemsOrderByAnswers(currentAnswers);
            orderingItemsDao.setItemsOrder(answersOrder);
        }
        return answersOrder;
    }
}
