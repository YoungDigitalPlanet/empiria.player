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

package eu.ydp.empiria.player.client.controller;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

public class PageControllerJunitTestBase {

    protected ItemController itemController;
    protected ItemBody itemBody;
    protected Item item;

    protected PageController pageController;

    @Before
    public void prepare() {
        pageController = mock(PageController.class);
        doCallRealMethod().when(pageController).hasInteractiveModules();
    }

    /**
     * Creates and fills mock of ItemController instance
     *
     * @param modules
     */
    protected void prepareItemControllerMock(List<IModule> modules) {
        itemBody = mock(ItemBody.class);
        itemBody.modules = modules;

        item = mock(Item.class);
        item.itemBody = itemBody;

        itemController = mock(ItemController.class);
        itemController.item = item;

        pageController.items = Lists.newArrayList(itemController);

        doCallRealMethod().when(itemController).hasInteractiveModules();
        doCallRealMethod().when(itemBody).hasInteractiveModules();
        doCallRealMethod().when(item).hasInteractiveModules();
    }

    /**
     * @param varargs list of module types, ie. MathModule.class, IInteractionModule.class
     * @return list of mocked modules
     */
    protected List<IModule> prepareMockModulesList(Class<? extends IModule>... types) {
        List<IModule> modules = new ArrayList<IModule>();
        for (Class<? extends IModule> type : types) {
            modules.add(mock(type));
        }
        return modules;
    }
}
