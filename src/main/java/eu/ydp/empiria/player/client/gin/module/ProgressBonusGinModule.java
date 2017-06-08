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

import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.ProgressBonusConfigModuleScopeProvider;
import eu.ydp.empiria.player.client.module.progressbonus.presenter.ProgressBonusPresenter;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusViewImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class ProgressBonusGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(ProgressBonusView.class).to(ProgressBonusViewImpl.class);
        bind(ProgressBonusConfig.class).annotatedWith(ModuleScoped.class).toProvider(ProgressBonusConfigModuleScopeProvider.class);
        bindModuleScoped(ProgressBonusPresenter.class, new TypeLiteral<ModuleScopedProvider<ProgressBonusPresenter>>() {
        });
        bindModuleScoped(ProgressBonusView.class, new TypeLiteral<ModuleScopedProvider<ProgressBonusView>>() {
        });
    }
}
