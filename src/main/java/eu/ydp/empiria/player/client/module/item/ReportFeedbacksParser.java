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

import com.google.common.collect.Range;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class ReportFeedbacksParser {

    public ProgressToStringRangeMap parse(NodeList feedbacks) {
        ProgressToStringRangeMap reportFeedbacks = new ProgressToStringRangeMap();

        for (int i = 0; i < feedbacks.getLength(); i++) {
            Element feedbackElement = (Element) feedbacks.item(i);

            Range<Integer> range = getRangeFromElement(feedbackElement);
            String feedback = feedbackElement.getFirstChild().toString();

            reportFeedbacks.addValueForRange(range, feedback);
        }
        return reportFeedbacks;
    }

    private Range<Integer> getRangeFromElement(Element feedbackElement) {
        int from = Integer.parseInt(feedbackElement.getAttribute("from"));
        int to = Integer.parseInt(feedbackElement.getAttribute("to"));
        return Range.closed(from, to);
    }
}
