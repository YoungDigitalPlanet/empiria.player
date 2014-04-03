package eu.ydp.empiria.player.client.module.item;


import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import org.junit.Test;

public class ReportFeedbacksParserTest extends GWTTestCase {

	private ReportFeedbacksParser testObj;

	private final String FEEDBACK_FOR_10_19 = "Feedback for 10-19";
	private final String FEEDBACK_FOR_20_99 = "Feedback for 20-99";

	//@formatter:off
	private final String INPUT = "" +
			"<reportFeedbacks>" +
				"<reportFeedback from=\"10\" to=\"19\">" +
					FEEDBACK_FOR_10_19 +
				"</reportFeedback>" +
				"<reportFeedback from=\"20\" to=\"99\">" +
					FEEDBACK_FOR_20_99 +
				"</reportFeedback>" +
			"</reportFeedbacks>";
	//@formatter:on


	@Override
	public void gwtSetUp() {
		testObj = new ReportFeedbacksParser();
	}

	@Test
	public void testCreateReportFeedbacksFromNodeList() {
		// given
		NodeList feedbacks = XMLParser.parse(INPUT).getElementsByTagName("reportFeedback");

		// when
		ProgressToStringRangeMap reportFeedbacks = testObj.parse(feedbacks);

		// then
		assertEquals(reportFeedbacks.getValueForProgress(0), "");
		assertEquals(reportFeedbacks.getValueForProgress(10), FEEDBACK_FOR_10_19);
		assertEquals(reportFeedbacks.getValueForProgress(99), FEEDBACK_FOR_20_99);
		assertEquals(reportFeedbacks.getValueForProgress(100), "");
	}

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}
}
