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

package eu.ydp.empiria.player.client.controller.variables.processor.item;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

import java.util.Map;

public class FeedbackAutoMarkInterpreter {

    private static final String VALUE = "value";
    private static final String FEEDBACK_INLINE = "feedbackInline";
    private static final String VARIABLE_IDENTIFIER = "variableIdentifier";
    private static final String MARK = "mark";

    public void interpretFeedbackAutoMark(Node node, ItemResponseManager responseManager) {
        NodeList feedbacks = ((Element) node).getElementsByTagName(FEEDBACK_INLINE);

        for (int feedbackNumber = 0; feedbackNumber < feedbacks.getLength(); feedbackNumber++) {
            Node currentFeedback = feedbacks.item(feedbackNumber);

            if (elementHasAttributes((Element) currentFeedback, VARIABLE_IDENTIFIER, VALUE, MARK)) {
                interpretSingleFeedbackAutoMark(responseManager, currentFeedback);
            }
        }
    }

    private void interpretSingleFeedbackAutoMark(ItemResponseManager responseManager, Node feedback) {
        NamedNodeMap attributes = feedback.getAttributes();
        Node markedNode = attributes.getNamedItem(MARK);
        if (markedNode.getNodeValue().toUpperCase().compareTo("AUTO") == 0) {

            String variableIdentifier = attributes.getNamedItem(VARIABLE_IDENTIFIER).getNodeValue();
            if (responseManager.containsResponse(variableIdentifier)) {
                boolean correctness;
                String feedbackValue = attributes.getNamedItem(VALUE).getNodeValue();

                if (stringContainsAnyOf(feedbackValue, ".", "*", "[", "(", "\\", "^", "$", "]", ")")) {
                    String responseCorrectAnswers = responseManager.getVariable(variableIdentifier).getCorrectAnswersValuesShort();
                    correctness = responseCorrectAnswers.matches(feedbackValue);

                } else {
                    correctness = responseManager.getVariable(variableIdentifier).compareValues(feedbackValue.split(";"));
                }

                if (correctness) {
                    markedNode.setNodeValue("CORRECT");
                } else {
                    markedNode.setNodeValue("WRONG");
                }
            }
        }
    }

    private boolean elementHasAttributes(Element element, String... attributes) {
        for (String attribute : attributes) {
            if (!element.hasAttribute(attribute)) {
                return false;
            }
        }
        return true;
    }

    private boolean stringContainsAnyOf(String object, String... subStrings) {
        for (String subString : subStrings) {
            if (object.contains(subString)) {
                return true;
            }
        }
        return false;
    }
}
