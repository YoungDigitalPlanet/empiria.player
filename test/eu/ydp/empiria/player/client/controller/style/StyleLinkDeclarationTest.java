package eu.ydp.empiria.player.client.controller.style;

import java.util.List;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class StyleLinkDeclarationTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}

	public void testUserAgent() {
		String xml = "<styleDeclaration><link href=\"common/default.css\" userAgent=\"^((?!(iPad|iPhone)).)*\"/><link href=\"common/ipad.css\" userAgent=\".*iPad.*\"/><link href=\"common/iphone.css\" userAgent=\".*iPhone.*\"/></styleDeclaration>";
		String baseUrl = "";
		NodeList nodes = XMLParser.parse(xml).getElementsByTagName("styleDeclaration");
		StyleLinkDeclaration sld = new StyleLinkDeclaration(nodes, baseUrl);
		List<String> output;

		output = sld
				.getStyleLinksForUserAgent("Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405");
		assertEquals(1, output.size());
		assertTrue(output.contains("/common/ipad.css"));

		output = sld
				.getStyleLinksForUserAgent("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
		assertEquals(1, output.size());
		assertTrue(output.contains("/common/iphone.css"));

		output = sld
				.getStyleLinksForUserAgent("Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)");
		assertEquals(1, output.size());
		assertTrue(output.contains("/common/default.css"));
	}

}
