package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

public class HintJsonReportTest extends AbstractEmpiriaPlayerGWTTestCase {

	public void testGeneratingJSON() {
		HintJsonReport hints = HintJsonReport.create();

		hints.setChecks(1);
		hints.setMistakes(2);
		hints.setShowAnswers(3);
		hints.setReset(4);

		assertEquals("JSON string", "{\"checks\":1, \"mistakes\":2, \"showAnswers\":3, \"reset\":4}", hints.getJSONString());
	}

}
