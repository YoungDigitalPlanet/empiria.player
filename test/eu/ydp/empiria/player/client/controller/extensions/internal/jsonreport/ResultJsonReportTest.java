package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

public class ResultJsonReportTest extends AbstractEmpiriaPlayerGWTTestCase {

	public void testGeneratingJSON() {
		ResultJsonReport result = ResultJsonReport.create();

		result.setTodo(2);
		result.setDone(3);
		result.setResult(33);
		result.setErrors(8);

		assertEquals("JSON string", "{\"todo\":2, \"done\":3, \"result\":33, \"errors\":8}", result.getJSONString());
	}
}
