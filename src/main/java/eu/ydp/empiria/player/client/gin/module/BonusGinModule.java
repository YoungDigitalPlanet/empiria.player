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
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusConfig;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.BonusConfigModuleScopeProvider;
import eu.ydp.empiria.player.client.module.bonus.BonusProvider;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupView;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupViewImpl;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.NullPowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.NullPowerFeedbackTutorClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackBonusClient;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackTutorClient;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class BonusGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(BonusPopupView.class).to(BonusPopupViewImpl.class);
        bind(PowerFeedbackBonusClient.class).to(NullPowerFeedbackBonusClient.class);
        bind(PowerFeedbackTutorClient.class).to(NullPowerFeedbackTutorClient.class);
        bind(BonusConfig.class).annotatedWith(ModuleScoped.class).toProvider(BonusConfigModuleScopeProvider.class);
        bindModuleScoped(BonusProvider.class, new TypeLiteral<ModuleScopedProvider<BonusProvider>>() {
        });

    }

}
