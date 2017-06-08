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

package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusService;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;

public class BonusConfigModuleScopeProvider implements Provider<BonusConfig> {

    private static final String BONUS_ID_ATTR = "bonusId";

    @Inject
    private ModuleScopeStack moduleScopeStack;

    @Inject
    private BonusService bonusService;

    @Override
    public BonusConfig get() {
        ModuleCreationContext context = moduleScopeStack.getCurrentTopContext();
        String bonusId = context.getXmlElement().getAttribute(BONUS_ID_ATTR);
        return bonusService.getBonusConfig(bonusId);
    }

}
