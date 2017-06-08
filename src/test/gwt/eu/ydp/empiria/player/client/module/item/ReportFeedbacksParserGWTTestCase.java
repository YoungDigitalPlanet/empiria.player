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

package eu.ydp.empiria.player.client.module.item;

import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class ReportFeedbacksParserGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private ReportFeedbacksParser testObj;

    private final String FEEDBACK_FOR_10_19 = "Feedback for 10-19";
    private final String FEEDBACK_FOR_20_99 = "Feedback for 20-99";

    //@formatter:off
    private final String INPUT = "" +
            "<reportFeedback>" +
            "<reportFeedbackText from=\"10\" to=\"19\">" +
            FEEDBACK_FOR_10_19 +
            "</reportFeedbackText>" +
            "<reportFeedbackText from=\"20\" to=\"99\">" +
            FEEDBACK_FOR_20_99 +
            "</reportFeedbackText>" +
            "</reportFeedback>";
    //@formatter:on

    @Override
    public void gwtSetUp() {
        testObj = new ReportFeedbacksParser();
    }

    public void testCreateReportFeedbacksFromNodeList() {
        // given
        NodeList feedbacks = XMLParser.parse(INPUT).getElementsByTagName("reportFeedbackText");

        // when
        ProgressToStringRangeMap reportFeedbacks = testObj.parse(feedbacks);

        // then
        assertEquals(reportFeedbacks.getValueForProgress(0), "");
        assertEquals(reportFeedbacks.getValueForProgress(10), FEEDBACK_FOR_10_19);
        assertEquals(reportFeedbacks.getValueForProgress(99), FEEDBACK_FOR_20_99);
        assertEquals(reportFeedbacks.getValueForProgress(100), "");
    }
}
