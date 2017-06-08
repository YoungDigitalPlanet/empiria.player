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

import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js.BonusConfigJs;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

import java.util.List;

public class BonusConfigGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private static final String JSON_CONFIG = "" + "{" + "	\"actions\": [" + "		{" + "			\"type\": \"ON_PAGE_ALL_OK_FIRST_TIME\"," + "			\"bonuses\": ["
            + "				{" + "					\"asset\": \"alex.png\"," + "					\"type\": \"IMAGE\"," + "					\"width\": 111," + "					\"height\": 222" + "				}," + "				{"
            + "					\"asset\": \"animation1\"," + "					\"type\": \"SWIFFY\"," + "					\"width\": 333," + "					\"height\": 444" + "				}" + "			]" + "		}"
            + "	]" + "}";

    private final OverlayTypesParser overlayTypesParser = new OverlayTypesParser();
    private BonusConfigJs bonusConfigJs;

    public void testShouldParseBonusConfigFromJs() throws Exception {
        // given
        assertTrue(overlayTypesParser.isValidJSON(JSON_CONFIG));
        bonusConfigJs = overlayTypesParser.get(JSON_CONFIG);

        // when
        BonusConfig bonusConfig = BonusConfig.fromJs(bonusConfigJs);

        // then
        assertNotNull(bonusConfig);
        List<BonusAction> actions = bonusConfig.getActions();

        assertCorrectAction(actions);
    }

    private void assertCorrectAction(List<BonusAction> actions) {
        assertNotNull(actions);
        assertEquals(1, actions.size());

        BonusAction bonusAction = actions.get(0);
        assertEquals(BonusActionType.ON_PAGE_ALL_OK_FIRST_TIME, bonusAction.getType());

        assertCorrectBonuses(bonusAction.getBonuses());
    }

    private void assertCorrectBonuses(List<BonusResource> bonuses) {
        assertNotNull(bonuses);
        assertEquals(2, bonuses.size());

        assertCorrectBonus(bonuses.get(0), "alex.png", BonusResourceType.IMAGE, 111, 222);
        assertCorrectBonus(bonuses.get(1), "animation1", BonusResourceType.SWIFFY, 333, 444);
    }

    private void assertCorrectBonus(BonusResource bonusResource, String asset, BonusResourceType type, int width, int height) {
        assertNotNull(bonusResource);
        assertEquals(asset, bonusResource.getAsset());
        assertEquals(type, bonusResource.getType());
        assertEquals(width, bonusResource.getSize().getWidth());
        assertEquals(height, bonusResource.getSize().getHeight());
    }
}
