package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;

public class InlineBodyGeneratorGWTTest extends AbstractEmpiriaPlayerGWTTestCase {

	private InlineBodyGenerator testObj;

	private class MockModuleRegistry implements ModulesRegistrySocket {

		@Override
		public boolean isModuleSupported(String nodeName) {
			return true;
		}

		@Override
		public boolean isMultiViewModule(String nodeName) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isInlineModule(String nodeName) {
			return false;
		}

		@Override
		public IModule createModule(Element node) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Override
	public void gwtSetUp() {
		testObj = new InlineBodyGenerator(new MockModuleRegistry(), null, null, null, null);
	}

	public void test_generateForEmptyXmlDocument() {
		// given
		Document document = XMLParser.createDocument();

		// when
		Widget body = testObj.generateInlineWidget(document);

		// then
		assertNotNull(body);
		assertEquals("<div class=\"qp-text-inline\"></div>", body.toString());
	}
	
	public void test_generatePanelForEmptyXmlDocument() {
		// given
		Document document = XMLParser.createDocument();

		// when
		Widget body = testObj.generateInlinePanelWidget(document);

		// then
		assertNotNull(body);
		assertEquals("<div class=\"qp-text-inline\"></div>", body.toString());
	}

	public void test_generateWidgetForNonEmptyDocument() {
		// given
		Document document = XMLParser.parse("<span>Copyright © Young Digital Planet</span>");

		// when
		Widget body = testObj.generateInlineWidget(document);

		// then
		assertNotNull(body);
		assertEquals("<div class=\"qp-text-inline\"></div>", body.toString());
	}

	public void test_generatePanelForNonEmptyDocument() {
		// given
		Document document = XMLParser.parse("<span>Copyright © Young Digital Planet</span>");

		// when
		Widget body = testObj.generateInlinePanelWidget(document);

		// then
		assertNotNull(body);
		assertEquals("<div class=\"qp-text-inline\"><span>Copyright © Young Digital Planet</span></div>", body.toString());
	}

}
