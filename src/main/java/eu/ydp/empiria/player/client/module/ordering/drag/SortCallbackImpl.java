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

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.gin.module.ModuleScopedLazyProvider;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

public class SortCallbackImpl implements SortCallback {

    @Inject
    private ModuleScopedLazyProvider<OrderInteractionPresenter> presenterProvider;
    @Inject
    private TouchController touchController;
    @Inject
    @ModuleScoped
    private OrderingItemsDao orderingItemsDao;

    @Override
    public void sortStoped(int from, int to) {
        List<String> itemsInOrder = orderingItemsDao.getItemsOrder();

        String movedElement = itemsInOrder.remove(from);
        itemsInOrder.add(to, movedElement);

        presenterProvider.get().updateItemsOrder(itemsInOrder);
    }

    @Override
    public void setSwypeLock(boolean lock) {
        touchController.setSwypeLock(lock);
    }
}
