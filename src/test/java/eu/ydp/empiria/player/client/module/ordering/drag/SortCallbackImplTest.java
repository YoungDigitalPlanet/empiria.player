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

package eu.ydp.empiria.player.client.module.ordering.drag;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.gin.module.ModuleScopedLazyProvider;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SortCallbackImplTest {

    @InjectMocks
    private SortCallbackImpl testObj;
    @Mock
    private ModuleScopedLazyProvider<OrderInteractionPresenter> presenterProvider;
    @Mock
    private OrderingItemsDao orderingItemsDao;
    @Mock
    private TouchController touchController;

    @Test
    public void shouldTestName() {
        // given
        List<String> items = Lists.newArrayList("a", "b", "c");
        final List<String> EXPECTED_ITEMS_ORDER = ImmutableList.of("a", "c", "b");
        when(orderingItemsDao.getItemsOrder()).thenReturn(items);
        OrderInteractionPresenter presenter = mock(OrderInteractionPresenter.class);
        when(presenterProvider.get()).thenReturn(presenter);
        final int FROM = 1;
        final int TO = 2;

        // when
        testObj.sortStoped(FROM, TO);

        // then
        verify(presenter).updateItemsOrder(EXPECTED_ITEMS_ORDER);
    }

    @Test
    public void shouldLockSwype() {
        // when
        testObj.setSwypeLock(true);
        // then
        verify(touchController).setSwypeLock(true);
    }

    @Test
    public void shouldNotLockSwype() {
        // when
        testObj.setSwypeLock(false);
        // then
        verify(touchController).setSwypeLock(false);
    }

}
