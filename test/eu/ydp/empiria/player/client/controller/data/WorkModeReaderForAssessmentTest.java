package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class WorkModeReaderForAssessmentTest extends GWTTestCase {

	private WorkModeReaderForAssessment testObj;

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}

	@Override
	protected void gwtSetUp() {
		testObj = new WorkModeReaderForAssessment();
	}

	public void test_shouldReturnModeWhenModeIsSpecified() {
		// given
		XmlData xmlData = getXmlData("<assessmentTest compilerVersion=\"3.3.2.117\" mode=\"test\"/>");
		String expected = "test";

		// when
		String actual = testObj.read(xmlData);

		// then
		assertEquals(actual, expected);
	}

	public void test_shouldReturnEmptyStringWhenModeIsNotSpecified() {
		// given
		XmlData xmlData = getXmlData("<assessmentTest compilerVersion=\"3.3.2.117\"/>");
		String expected = "";

		// when
		String actual = testObj.read(xmlData);

		// then
		assertEquals(actual, expected);
	}

	private XmlData getXmlData(String source) {
		Document doc = XMLParser.parse(source);
		String url = "url";
		return new XmlData(doc, url);
	}
}
