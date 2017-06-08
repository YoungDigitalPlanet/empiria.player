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
import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.List;

public class ItemsOrderByAnswersFinderJUnitTest {

    private ItemsOrderByAnswersFinder itemsOrderByAnswersFinder = new ItemsOrderByAnswersFinder();

    @Test
    public void shouldFindCorrectItemsOrderByUserAnswers() throws Exception {
        List<String> currentAnswers = Lists.newArrayList("answer1", "answer3", "answer2");
        List<OrderingItem> items = Lists.newArrayList(createItem("id2", "answer2"), createItem("id1", "answer1"), createItem("id3", "answer3"));
        List<String> itemsOrder = itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(currentAnswers, items);

        List<String> expectedItemsOrder = Lists.newArrayList("id1", "id3", "id2");
        Assertions.assertThat(itemsOrder).isEqualTo(expectedItemsOrder);
    }

    @Test(expected = CannotMatchOrderingItemsToUserAnswersException.class)
    public void shouldThrowExceptionWhenCannotMatchOrderingItemsToUserAnswer() throws Exception {
        List<String> currentAnswers = Lists.newArrayList("not match any OrderingItem", "answer3", "answer2");
        List<OrderingItem> items = Lists.newArrayList(createItem("id2", "answer2"), createItem("id1", "answer1"), createItem("id3", "answer3"));

        itemsOrderByAnswersFinder.findCorrectItemsOrderByAnswers(currentAnswers, items);
    }

    private OrderingItem createItem(String id, String answerValue) {
        return new OrderingItem(id, answerValue);
    }

}
