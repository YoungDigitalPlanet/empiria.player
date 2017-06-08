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

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderingShowingAnswersControllerJUnitTest {

    private OrderingShowingAnswersController controller;
    @Mock
    private OrderingItemsDao orderingItemsDao;
    @Mock
    private ItemsResponseOrderController itemsResponseOrderController;
    @Mock
    private OrderInteractionModuleModel model;

    @Before
    public void setUp() throws Exception {
        controller = new OrderingShowingAnswersController(itemsResponseOrderController, orderingItemsDao, model);
    }

    @Test
    public void shouldShowCorrectAnswers() throws Exception {
        // given
        ShowAnswersType mode = ShowAnswersType.CORRECT;

        List<String> correctAnswers = Lists.newArrayList("correctAnswers");
        when(model.getCorrectAnswers()).thenReturn(correctAnswers);

        List<String> newAnswersOrder = Lists.newArrayList("newAnswersOrder");
        when(itemsResponseOrderController.getCorrectItemsOrderByAnswers(correctAnswers)).thenReturn(newAnswersOrder);

        // when
        List<String> result = controller.findNewAnswersOrderToShow(mode);

        // then
        assertEquals(newAnswersOrder, result);
    }

    @Test
    public void shouldShowAnswersSpecyfiedByCurrentAnswers() throws Exception {
        // given
        ShowAnswersType mode = ShowAnswersType.USER;

        List<String> currentAnswers = Lists.newArrayList("correctAnswers");
        when(model.getCurrentAnswers()).thenReturn(currentAnswers);

        List<String> newAnswersOrder = Lists.newArrayList("newAnswersOrder");
        when(itemsResponseOrderController.getCorrectItemsOrderByAnswers(currentAnswers)).thenReturn(newAnswersOrder);

        // when
        List<String> result = controller.findNewAnswersOrderToShow(mode);

        // then
        assertEquals(newAnswersOrder, result);
        verify(orderingItemsDao).setItemsOrder(newAnswersOrder);
    }
}
