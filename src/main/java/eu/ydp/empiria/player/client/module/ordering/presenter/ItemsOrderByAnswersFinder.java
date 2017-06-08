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
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;

import java.util.Collection;
import java.util.List;

public class ItemsOrderByAnswersFinder {

    public List<String> findCorrectItemsOrderByAnswers(List<String> currentAnswers, Collection<OrderingItem> items) {
        List<String> itemsIdOrder = Lists.newArrayList();

        for (int i = 0; i < currentAnswers.size(); i++) {
            String currentAnswer = currentAnswers.get(i);
            OrderingItem item = findItemWithAnswer(currentAnswer, items);
            String itemId = item.getId();
            itemsIdOrder.add(itemId);

            items.remove(item);
        }

        return itemsIdOrder;
    }

    private OrderingItem findItemWithAnswer(String currentAnswer, Collection<OrderingItem> items) {
        for (OrderingItem orderingItem : items) {
            if (orderingItem.getAnswerValue().equals(currentAnswer)) {
                return orderingItem;
            }
        }
        throw new CannotMatchOrderingItemsToUserAnswersException("Cannot match ordering items to user answers!");
    }

}
