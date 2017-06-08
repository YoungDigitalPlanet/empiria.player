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

package eu.ydp.empiria.player.client.module.ordering.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

public class OrderingItemsDaoJUnitTest {

    private OrderingItemsDao orderingItemsDao;

    @Before
    public void setUp() throws Exception {
        orderingItemsDao = new OrderingItemsDao();
    }

    @Test
    public void shouldCreateInitialItemsOrder() throws Exception {
        addItem("item1");
        addItem("item2");

        assertNull(orderingItemsDao.getItemsOrder());

        orderingItemsDao.createInitialItemsOrder();

        List<String> itemsOrder = orderingItemsDao.getItemsOrder();
        assertThat(itemsOrder).containsOnly("item1", "item2");
    }

    private void addItem(String id) {
        OrderingItem orderingItem = new OrderingItem(id, "whatever");
        orderingItemsDao.addItem(orderingItem);
    }
}
