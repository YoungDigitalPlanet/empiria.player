package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class FeedbackAutoMarkInterpreter {

	private static final String VALUE = "value";
	private static final String FEEDBACK_INLINE = "feedbackInline";
	private static final String VARIABLE_IDENTIFIER = "variableIdentifier";
	private static final String MARK = "mark";

	public void interpretFeedbackAutoMark(Node node, Map<String, Response> responses) {
		NodeList feedbacks = ((Element) node).getElementsByTagName(FEEDBACK_INLINE);

		for (int feedbackNumber = 0; feedbackNumber < feedbacks.getLength(); feedbackNumber++) {
			Node currentFeedback = feedbacks.item(feedbackNumber);

			if (elementHasAttributes((Element) currentFeedback, VARIABLE_IDENTIFIER, VALUE, MARK)) {
				interpretSingleFeedbackAutoMark(responses, currentFeedback);
			}
		}
	}

	private void interpretSingleFeedbackAutoMark(Map<String, Response> responses, Node feedback) {
		NamedNodeMap attributes = feedback.getAttributes();
		Node markedNode = attributes.getNamedItem(MARK);
		if (markedNode.getNodeValue().toUpperCase().compareTo("AUTO") == 0) {

			String variableIdentifier = attributes.getNamedItem(VARIABLE_IDENTIFIER).getNodeValue();
			if (responses.containsKey(variableIdentifier)) {
				boolean correctness = false;
				boolean correctnessFound = false;
				String feedbackValue = attributes.getNamedItem(VALUE).getNodeValue();

				if (stringContainsAnyOf(feedbackValue, ".", "*", "[", "(", "\\", "^", "$", "]", ")")) {
					String responseCorrectAnswers = responses.get(variableIdentifier).getCorrectAnswersValuesShort();
					correctness = responseCorrectAnswers.matches(feedbackValue);
					correctnessFound = true;

				} else {
					correctness = responses.get(variableIdentifier).compareValues(feedbackValue.split(";"));
					correctnessFound = true;
				}

				if (correctnessFound) {
					if (correctness) {
						markedNode.setNodeValue("CORRECT");
					} else {
						markedNode.setNodeValue("WRONG");
					}
				} else {
					markedNode.setNodeValue("NONE");
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
