package eu.ydp.empiria.player.client.controller.variables.manager;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.controller.variables.IVariableCreator;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public class VariableManagerTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}

	private class CustomVariable extends Variable {

		public CustomVariable() {
			super();
		}

		@Override
		public JSONValue toJSON() {
			return null;
		}

		@Override
		public void fromJSON(JSONValue value) {
		}

	}

	public void testXmlProcessing() {

		Document doc = XMLParser
				.parse("<nodes><responseDeclaration identifier='RESPONSE' cardinality='multiple' baseType='identifier'><correctResponse><value>ChoiceA</value></correctResponse></responseDeclaration></nodes>");

		VariableManager<CustomVariable> vm = new VariableManager<CustomVariable>(doc.getDocumentElement().getChildNodes(),

		new IVariableCreator<CustomVariable>() {

			@Override
			public CustomVariable createVariable(Node node) {
				assertTrue(node.getNodeType() == Node.ELEMENT_NODE);
				Element el = (Element) node;
				String identifier = el.getAttribute("identifier");
				assertEquals("RESPONSE", identifier);
				return new CustomVariable();
			}
		}

		);

		assertTrue(vm.getVariablesMap().size() == 1);
		assertTrue(vm.getVariable("RESPONSE") instanceof CustomVariable);
	}

}
