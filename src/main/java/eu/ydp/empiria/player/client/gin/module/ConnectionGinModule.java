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

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceImpl;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceView;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceViewImpl;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItemImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenter;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenterImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModuleViewImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionViewVertical;

public class ConnectionGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ConnectionModulePresenter.class).to(ConnectionModulePresenterImpl.class);
        bind(ConnectionView.class).to(ConnectionViewVertical.class);
        bind(MultiplePairModuleView.class).to(ConnectionModuleViewImpl.class);

        install(new GinFactoryModuleBuilder().implement(ConnectionItem.class, ConnectionItemImpl.class)
                .implement(ConnectionSurface.class, ConnectionSurfaceImpl.class).implement(MultiplePairModuleView.class, ConnectionModuleViewImpl.class)
                .implement(ConnectionView.class, ConnectionViewVertical.class).implement(ConnectionSurfaceView.class, ConnectionSurfaceViewImpl.class)
                .build(ConnectionModuleFactory.class));
        install(new GinFactoryModuleBuilder().build(ConnectionItemsFactory.class));
    }

}
