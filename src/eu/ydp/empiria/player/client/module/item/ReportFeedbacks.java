package eu.ydp.empiria.player.client.module.item;

import com.google.common.collect.Range;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class ReportFeedbacks extends ProgressToStringRangeMap {

	public static ReportFeedbacks fromElement(NodeList feedbacks) {
		ReportFeedbacks reportFeedbacks = new ReportFeedbacks();

		for (int i = 0; i < feedbacks.getLength(); i++ ){
			Element feedbackElement = (Element) feedbacks.item(i);

			Range<Integer> range = getRangeFromElement(feedbackElement);
			String feedback = feedbackElement.getFirstChild().toString();

			reportFeedbacks.addValueForRange(range, feedback);
		}
		return reportFeedbacks;
	}

	private static Range<Integer> getRangeFromElement(Element feedbackElement) {
		int from = Integer.parseInt(feedbackElement.getAttribute("from"));
		int to = Integer.parseInt(feedbackElement.getAttribute("to"));
		return Range.closed(from, to);
	}
}
