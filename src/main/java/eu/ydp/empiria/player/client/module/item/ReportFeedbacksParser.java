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
