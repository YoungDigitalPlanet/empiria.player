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

public class ItemJsonReportGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testGeneratingJSON() {
        ItemJsonReport item = ItemJsonReport.create();
        ResultJsonReport result = ResultJsonReport.create("{\"todo\":2, \"done\":3}");
        HintJsonReport hints = HintJsonReport.create("{\"errors\":2, \"mistakes\":3}");

        item.setIndex(1);
        item.setTitle("Item title");
        item.setResult(result);
        item.setHints(hints);

        assertEquals("JSON String", "{\"index\":1, \"title\":\"Item title\", \"result\":{\"todo\":2, \"done\":3}, \"hints\":{\"errors\":2, \"mistakes\":3}}",
                item.getJSONString());
    }

}
