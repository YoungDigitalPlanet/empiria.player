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

package eu.ydp.empiria.player.client.controller.extensions.internal.progressbonus;

import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressAssetConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressBonusConfigJs;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

import java.util.List;

public class ProgressBonusConfigGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private final OverlayTypesParser overlayTypesParser = new OverlayTypesParser();

    private static final String JSON_CONFIG = "" + "{" + "\"progresses\": " + "	[" + "		{" + "			\"from\": 0," + "			\"assets\": " + "				[" + "					{"
            + "						\"asset\": \"report_award_off.png\"," + "						\"width\": 111," + "						\"height\": 222" + "					}" + "				]" + "		}," + "		{"
            + "			\"from\": 100," + "			\"assets\": " + "				[" + "					{" + "						\"asset\": \"report_award1_on_%.png\"," + "						\"count\": 2,"
            + "						\"width\": 333," + "						\"height\": 444" + "					}," + "					{" + "						\"asset\": \"report_award2_on_%.png\"," + "						\"count\": 3,"
            + "						\"width\": 555," + "						\"height\": 666" + "					}" + "				]" + "		}" + "	]" + "}";

    public void testShouldParseProgressBonusConfigFromJs() {
        // given
        assertTrue(overlayTypesParser.isValidJSON(JSON_CONFIG));
        ProgressBonusConfigJs configJs = overlayTypesParser.get(JSON_CONFIG);

        // when
        ProgressBonusConfig progressBonusConfig = ProgressBonusConfig.fromJs(configJs);

        // then
        assertProgresses(progressBonusConfig.getProgresses());
    }

    private void assertProgresses(List<ProgressConfig> progresses) {

        ProgressConfig progress1 = progresses.get(0);
        assertEquals(0, progress1.getFrom());
        assertAsset(progress1.getAssets().get(0), "report_award_off.png", 1, 111, 222);

        ProgressConfig progress2 = progresses.get(1);
        assertEquals(100, progress2.getFrom());
        assertAsset(progress2.getAssets().get(0), "report_award1_on_%.png", 2, 333, 444);
        assertAsset(progress2.getAssets().get(1), "report_award2_on_%.png", 3, 555, 666);
    }

    private void assertAsset(ProgressAssetConfig asset, String assetPath, int count, int width, int height) {
        assertEquals(assetPath, asset.getPath());
        assertEquals(count, asset.getCount());
        assertEquals(width, asset.getSize().getWidth());
        assertEquals(height, asset.getSize().getHeight());
    }
}
