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

package eu.ydp.empiria.player.client.module.bonus;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusAction;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusActionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResource;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.RandomWrapper;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Iterables.find;
import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusActionType.ON_PAGE_ALL_OK_FIRST_TIME;

public class BonusProvider {

    @Inject
    @ModuleScoped
    private BonusConfig bonusConfig;
    @Inject
    private BonusFactory bonusFactory;
    @Inject
    private RandomWrapper randomWrapper;

    public Bonus next() {
        BonusAction action = findAction(ON_PAGE_ALL_OK_FIRST_TIME);
        BonusResource bonusResource = pickBonusResource(action.getBonuses());
        return bonusFactory.createBonus(bonusResource);
    }

    private BonusResource pickBonusResource(List<BonusResource> bonuses) {
        checkArgument(!bonuses.isEmpty(), "Bonuses list should not be empty - invalid bonus configuration.");
        int nextIndex = randomWrapper.nextInt(bonuses.size());
        return bonuses.get(nextIndex);
    }

    private BonusAction findAction(final BonusActionType onPageAllOkFirstTime) {
        return find(bonusConfig.getActions(), new Predicate<BonusAction>() {

            @Override
            public boolean apply(BonusAction testAction) {
                return onPageAllOkFirstTime.equals(testAction.getType());
            }
        });
    }
}
