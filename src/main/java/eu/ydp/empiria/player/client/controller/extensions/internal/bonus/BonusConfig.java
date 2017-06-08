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
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js.BonusConfigJs;

import java.util.List;

public class BonusConfig {

    private final List<BonusAction> actions;

    public BonusConfig(List<BonusAction> actions) {
        this.actions = actions;
    }

    public List<BonusAction> getActions() {
        return this.actions;
    }

    public static BonusConfig fromJs(BonusConfigJs bonusConfigJs) {
        List<BonusAction> actions = getActions(bonusConfigJs);
        return new BonusConfig(actions);
    }

    private static List<BonusAction> getActions(BonusConfigJs bonusConfigJs) {
        List<BonusAction> actions = Lists.newArrayList();
        JsArray<BonusActionJs> jsActions = bonusConfigJs.getActions();

        for (int i = 0; i < jsActions.length(); i++) {
            BonusActionJs jsAction = jsActions.get(i);
            BonusAction action = BonusAction.fromJs(jsAction);
            actions.add(action);
        }

        return actions;
    }
}
