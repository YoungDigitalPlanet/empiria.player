package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import java.util.List;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

public class AssessmentJsonReportTest extends AbstractEmpiriaPlayerGWTTestCase {

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
