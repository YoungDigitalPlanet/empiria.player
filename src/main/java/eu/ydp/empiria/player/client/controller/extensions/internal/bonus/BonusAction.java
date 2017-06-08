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

package eu.ydp.empiria.player.client.controller.extensions.internal.bonus;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.JsArray;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js.BonusActionJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js.BonusResourceJs;

import java.util.List;

public class BonusAction {

    private final List<BonusResource> bonuses;
    private final BonusActionType bonusActionType;

    public BonusAction(List<BonusResource> bonuses, BonusActionType bonusActionType) {
        this.bonuses = bonuses;
        this.bonusActionType = bonusActionType;
    }

    public BonusActionType getType() {
        return bonusActionType;
    }

    public List<BonusResource> getBonuses() {
        return bonuses;
    }

    public static BonusAction fromJs(BonusActionJs jsAction) {
        BonusActionType actionType = getBonusActionType(jsAction);
        List<BonusResource> bonuses = getBonuses(jsAction.getBonuses());
        return new BonusAction(bonuses, actionType);
    }

    private static List<BonusResource> getBonuses(JsArray<BonusResourceJs> jsBonuses) {
        List<BonusResource> bonuses = Lists.newArrayList();

        for (int i = 0; i < jsBonuses.length(); i++) {
            BonusResourceJs jsBonus = jsBonuses.get(i);
            BonusResource bonus = BonusResource.fromJs(jsBonus);
            bonuses.add(bonus);
        }

        return bonuses;
    }

    private static BonusActionType getBonusActionType(BonusActionJs jsAction) {
        String type = jsAction.getType();
        BonusActionType actionType = BonusActionType.valueOf(type);
        return actionType;
    }
}
