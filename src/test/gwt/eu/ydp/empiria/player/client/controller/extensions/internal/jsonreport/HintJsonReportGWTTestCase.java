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

package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class HintJsonReportGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testGeneratingJSON() {
        HintJsonReport hints = HintJsonReport.create();

        hints.setChecks(1);
        hints.setMistakes(2);
        hints.setShowAnswers(3);
        hints.setReset(4);

        assertEquals("JSON string", "{\"checks\":1, \"mistakes\":2, \"showAnswers\":3, \"reset\":4}", hints.getJSONString());
    }

}
