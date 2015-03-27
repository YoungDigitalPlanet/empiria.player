package eu.ydp.empiria.player.client.module.textentry.math;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.xml.client.Element;

import eu.ydp.gwtutil.xml.XMLParser;

public class MathSubAndSupUtilJUnitTest {

	MathSubAndSupUtil util;

	@Before
	public void before() {
		util = new MathSubAndSupUtil();
	}

	@Test
	public void shouldDetectMultiscript() {

		Element node = XMLParser.parse("<gap type=\"text-entry\" uid=\"uid_0000\" />").getDocumentElement();
		Element parentNode = XMLParser.parse(
				"<mmultiscripts>" + "<gap type=\"text-entry\" uid=\"uid_0000\" />" + "<none/>" + "<mprescripts/>" + "<none/>" + "<none/>" + "</mmultiscripts>")
				.getDocumentElement();

		assertTrue(util.isSubOrSup(node, parentNode));
	}
}
