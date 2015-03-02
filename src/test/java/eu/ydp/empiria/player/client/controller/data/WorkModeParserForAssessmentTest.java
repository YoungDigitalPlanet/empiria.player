package eu.ydp.empiria.player.client.controller.data;

import com.google.common.base.Optional;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class WorkModeParserForAssessmentTest extends AbstractEmpiriaPlayerGWTTestCase {

	private WorkModeParserForAssessment testObj;

	@Override
	protected void gwtSetUp() {
		testObj = new WorkModeParserForAssessment();
	}

	public void test_shouldReturnModeWhenModeIsCorrect() {
		// given
		XmlData xmlData = getXmlData("<assessmentTest compilerVersion=\"3.3.2.117\" mode=\"test\"/>");
		PlayerWorkMode expected = PlayerWorkMode.TEST;

		// when
		Optional<PlayerWorkMode> actual = testObj.parse(xmlData);

		// then
		assertEquals(actual.get(), expected);
	}

	public void test_shouldReturnAbsentWhenModeIsNotCorrect() {
		// given
		XmlData xmlData = getXmlData("<assessmentTest compilerVersion=\"3.3.2.117\" mode=\"not_existing_mode\"/>");

		// when
		Optional<PlayerWorkMode> actual = testObj.parse(xmlData);

		// then
		assertFalse(actual.isPresent());
	}

	public void test_shouldReturnAbsentWhenModeIsNotSpecified() {
		// given
		XmlData xmlData = getXmlData("<assessmentTest compilerVersion=\"3.3.2.117\"/>");

		// when
		Optional<PlayerWorkMode> actual = testObj.parse(xmlData);

		// then
		assertFalse(actual.isPresent());
	}

	private XmlData getXmlData(String source) {
		Document doc = XMLParser.parse(source);
		String url = "url";
		return new XmlData(doc, url);
	}
}
