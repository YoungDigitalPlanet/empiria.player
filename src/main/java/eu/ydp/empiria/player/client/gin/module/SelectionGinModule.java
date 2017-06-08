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
import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionViewBuilder;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenterImpl;
import eu.ydp.empiria.player.client.module.selection.view.*;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class SelectionGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(SelectionModulePresenter.class).to(SelectionModulePresenterImpl.class);
        bind(SelectionModuleView.class).to(SelectionModuleViewImpl.class);
        bind(SelectionElementGenerator.class).to(SelectionGridElementGeneratorImpl.class);
        bind(SelectionElementPositionGenerator.class).to(SelectionGridElementGeneratorImpl.class);

        bindModuleScoped(SelectionModuleModel.class, new TypeLiteral<ModuleScopedProvider<SelectionModuleModel>>() {
        });
        bindModuleScoped(SelectionModuleView.class, new TypeLiteral<ModuleScopedProvider<SelectionModuleView>>() {
        });
        bindModuleScoped(SelectionViewBuilder.class, new TypeLiteral<ModuleScopedProvider<SelectionViewBuilder>>() {
        });

        install(new GinFactoryModuleBuilder().build(SelectionModuleFactory.class));
    }
}
