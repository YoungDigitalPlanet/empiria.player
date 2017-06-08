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

package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.collect.ImmutableList;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactoryMock;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem.Column;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class ConnectionItemsJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.install(new FactoryModuleBuilder().build(ConnectionItemsFactory.class));
            binder.bind(ConnectionModuleFactory.class).toInstance(spy(new ConnectionModuleFactoryMock()));
        }
    }

    private ConnectionItems instance;
    private ImmutableList<String> idList;
    private ConnectionModuleFactory connectionModuleFactory;
    private InlineBodyGeneratorSocket bodyGeneratorSocket;

    @Before
    public void before() {
        GuiceModuleConfiguration config = new GuiceModuleConfiguration();
        setUpAndOverrideMainModule(config, new CustomGinModule());
        bodyGeneratorSocket = mock(InlineBodyGeneratorSocket.class);
        instance = injector.getInstance(ConnectionItemsFactory.class).getConnectionItems(bodyGeneratorSocket);
        connectionModuleFactory = injector.getInstance(ConnectionModuleFactory.class);
        idList = new ImmutableList.Builder<String>().add("id1").add("id2").add("id3").add("id4").build();
    }

    private void prepareItems() {
        for (String id : idList) {
            PairChoiceBean bean = mock(PairChoiceBean.class);
            doReturn(id).when(bean).getIdentifier();
            instance.addItemToLeftColumn(bean);
        }
    }

    @Test
    public void testAddItemToRightColumn() {
        for (String id : idList) {
            PairChoiceBean bean = mock(PairChoiceBean.class);
            doReturn(id).when(bean).getIdentifier();
            instance.addItemToRightColumn(bean);
        }

        verify(connectionModuleFactory, times(idList.size())).getConnectionItem(Matchers.any(PairChoiceBean.class), Matchers.eq(bodyGeneratorSocket),
                Matchers.eq(Column.RIGHT));
    }

    @Test
    public void testAddItemToLeftColumn() {
        prepareItems();

        verify(connectionModuleFactory, times(idList.size())).getConnectionItem(Matchers.any(PairChoiceBean.class), Matchers.eq(bodyGeneratorSocket),
                Matchers.eq(Column.LEFT));
    }

    @Test
    public void testGetConnectionItems() {
        List<ConnectionItem> leftItems = new ArrayList<ConnectionItem>();
        List<ConnectionItem> rightItems = new ArrayList<ConnectionItem>();

        for (int x = 0; x < 2; ++x) {
            String id = idList.get(x);
            PairChoiceBean bean = mock(PairChoiceBean.class);
            doReturn(id).when(bean).getIdentifier();
            leftItems.add(instance.addItemToLeftColumn(bean));
        }
        for (int x = 2; x < 4; ++x) {
            String id = idList.get(x);
            PairChoiceBean bean = mock(PairChoiceBean.class);
            doReturn(id).when(bean).getIdentifier();
            rightItems.add(instance.addItemToRightColumn(bean));
        }

        for (ConnectionItem leftItem : leftItems) {
            assertFalse(instance.getConnectionItems(leftItem).contains(leftItem));
        }
        for (ConnectionItem rightItem : rightItems) {
            assertFalse(instance.getConnectionItems(rightItem).contains(rightItem));
        }

    }

    @Test
    public void testResetAllItems() {
        List<ConnectionItem> items = new ArrayList<ConnectionItem>();
        for (String id : idList) {
            PairChoiceBean bean = mock(PairChoiceBean.class);
            doReturn(id).when(bean).getIdentifier();
            items.add(instance.addItemToLeftColumn(bean));
        }

        instance.resetAllItems();

        for (ConnectionItem item : items) {
            verify(item).reset();
        }
    }

    @Test
    public void testIsIdentifiersCorrect() {
        prepareItems();
        for (String id : idList) {
            assertTrue(instance.isIdentifiersCorrect(id));
        }
    }

    @Test
    public void testIsIdentifiersNotCorrect() {
        prepareItems();
        for (String id : idList) {
            assertFalse(instance.isIdentifiersCorrect(id + id));
        }

    }

    @Test
    public void testGetConnectionItem_ItemPresent() {
        prepareItems();
        for (String id : idList) {
            assertNotNull(instance.getConnectionItem(id));
        }
    }

    @Test
    public void testGetConnectionItem_ItemNotPresent() {
        for (String id : idList) {
            assertNull(instance.getConnectionItem(id));
        }
    }

    @Test
    public void testGetAllItems() {
        List<ConnectionItem> items = new ArrayList<ConnectionItem>();
        for (String id : idList) {
            PairChoiceBean bean = mock(PairChoiceBean.class);
            doReturn(id).when(bean).getIdentifier();
            items.add(instance.addItemToLeftColumn(bean));
        }

        assertTrue(items.containsAll(instance.getAllItems()));
        assertTrue(items.size() == instance.getAllItems().size());
    }

}
