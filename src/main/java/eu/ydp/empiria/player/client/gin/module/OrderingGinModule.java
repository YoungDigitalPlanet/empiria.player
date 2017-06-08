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

package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.drag.DragController;
import eu.ydp.empiria.player.client.module.ordering.drag.SortCallback;
import eu.ydp.empiria.player.client.module.ordering.drag.SortCallbackImpl;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.presenter.ItemsResponseOrderController;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenterImpl;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionViewImpl;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionViewWidget;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionViewWidgetImpl;
import eu.ydp.empiria.player.client.module.ordering.view.items.*;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class OrderingGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(OrderInteractionPresenter.class).to(OrderInteractionPresenterImpl.class);
        bind(OrderInteractionView.class).to(OrderInteractionViewImpl.class);
        bind(OrderInteractionViewWidget.class).to(OrderInteractionViewWidgetImpl.class);
        bind(OrderInteractionViewItems.class).to(OrderInteractionViewItemsImpl.class);
        bind(OrderInteractionViewItemStyles.class).to(OrderInteractionViewItemStylesImpl.class);
        bind(SortCallback.class).to(SortCallbackImpl.class);

        bindModuleScoped(OrderingItemsDao.class, new TypeLiteral<ModuleScopedProvider<OrderingItemsDao>>() {
        });
        bindModuleScoped(OrderInteractionModuleModel.class, new TypeLiteral<ModuleScopedProvider<OrderInteractionModuleModel>>() {
        });
        bindModuleScoped(OrderInteractionView.class, new TypeLiteral<ModuleScopedProvider<OrderInteractionView>>() {
        });
        bindModuleScoped(DragController.class, new TypeLiteral<ModuleScopedProvider<DragController>>() {
        });
        bindModuleScoped(ItemsResponseOrderController.class, new TypeLiteral<ModuleScopedProvider<ItemsResponseOrderController>>() {
        });
        bindModuleScoped(OrderInteractionPresenter.class, new TypeLiteral<ModuleScopedProvider<OrderInteractionPresenter>>() {
        });

        install(new GinFactoryModuleBuilder().implement(OrderInteractionViewItem.class, OrderInteractionViewItemImpl.class).build(
                OrderInteractionModuleFactory.class));
    }

}
