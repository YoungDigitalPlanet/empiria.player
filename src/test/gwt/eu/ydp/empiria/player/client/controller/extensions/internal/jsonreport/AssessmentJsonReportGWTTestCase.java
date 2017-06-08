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

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

import java.util.List;

public class AssessmentJsonReportGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testGeneratingJSON() {
        AssessmentJsonReport assessment = AssessmentJsonReport.create();
        ResultJsonReport assessmentResult = ResultJsonReport.create("{}");
        HintJsonReport assessmentHints = HintJsonReport.create("{}");
        List<ItemJsonReport> items = Lists.newArrayList(createItem("Page 1", 0), createItem("Page 2", 1), createItem("Page 1", 2));

        assessment.setTitle("Lesson title");
        assessment.setResult(assessmentResult);
        assessment.setHints(assessmentHints);
        assessment.setItems(items);

        assertEquals("JSON String", "{\"title\":\"Lesson title\", " + "\"result\":{}, " + "\"hints\":{}, " + "\"items\":["
                + "{\"index\":0, \"title\":\"Page 1\", " + "\"result\":{}, " + "\"hints\":{}}," + "{\"index\":1, \"title\":\"Page 2\", " + "\"result\":{}, "
                + "\"hints\":{}}," + "{\"index\":2, \"title\":\"Page 1\", " + "\"result\":{}, " + "\"hints\":{}}" + "]}", assessment.getJSONString());
    }

    private ItemJsonReport createItem(String title, int index) {
        ItemJsonReport item = ItemJsonReport.create();

        item.setIndex(index);
        item.setTitle(title);
        item.setResult(ResultJsonReport.create());
        item.setHints(HintJsonReport.create());

        return item;
    }

}
