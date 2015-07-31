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
