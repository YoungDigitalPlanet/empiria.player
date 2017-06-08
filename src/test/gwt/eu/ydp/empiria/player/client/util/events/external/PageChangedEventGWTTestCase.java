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

package eu.ydp.empiria.player.client.util.events.external;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class PageChangedEventGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public static final int EXPECTED_PAGE_INDEX = 7;
    public static final double DELTA = 0.1;

    public void testShouldSerializeEventWithTypeAndPageIndex() {
        // given
        PageChangedEvent testObj = new PageChangedEvent(EXPECTED_PAGE_INDEX);

        // when
        JavaScriptObject actual = testObj.getJSONObject();
        JSONObject actualJSON = new JSONObject(actual);

        // then
        double actualPage = actualJSON.get("payload").isObject().get("new_page").isNumber().doubleValue();
        assertEquals(actualPage, EXPECTED_PAGE_INDEX, DELTA);

        String actualEventType = actualJSON.get("type").isString().stringValue();
        assertEquals(actualEventType, "page_changed");
    }
}
