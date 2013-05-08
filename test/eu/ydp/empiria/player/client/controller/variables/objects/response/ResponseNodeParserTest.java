package eu.ydp.empiria.player.client.controller.variables.objects.response;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.gwtutil.xml.XMLParser;

public class ResponseNodeParserTest {

	private ResponseNodeParser responseNodeParser;

	@Before
	public void before() {
		responseNodeParser = new ResponseNodeParser();
	}

	@Test
	public void parseResponseFromNodeTest() {
		Response response = responseNodeParser.parseResponseFromNode(getResponseDeclaration());

		assertEquals(response.cardinality, Cardinality.MULTIPLE);
		assertEquals(response.identifier, "CONNECTION_RESPONSE_1");
		assertEquals(response.evaluate, Evaluate.CORRECT);
		assertEquals(response.baseType, BaseType.DIRECTED_PAIR);
		assertEquals(response.cardinality, Cardinality.MULTIPLE);
		assertEquals(response.getCheckMode(), CheckMode.EXPRESSION);
		assertEquals(2, response.correctAnswers.getAllAnswers().size());
		assertEquals(response.correctAnswers.getAllAnswers().get(0), "CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1");
		assertEquals(response.correctAnswers.getAllAnswers().get(1), "CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4");
		assertEquals(response.getID(), "CONNECTION_RESPONSE_1"); // checkMode
		assertEquals(response.getCountMode(), CountMode.SINGLE);
		// assertEquals(response, actual)
	}

	private Node getResponseDeclaration() {
		StringBuilder builder = new StringBuilder();
		builder.append("<responseDeclaration baseType=\"directedPair\" cardinality=\"multiple\" identifier=\"CONNECTION_RESPONSE_1\" checkMode=\"expression\" countMode=\"\" evaluate =\"correct\">");
		builder.append("		<correctResponse>");
		builder.append("			<value forIndex=\"0\"  group=\"x1\" groupMode=\"groupItem\">CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1</value>");
		builder.append("			<value>CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4</value>");
		builder.append("		</correctResponse>");
		builder.append("</responseDeclaration>");

		return XMLParser.parse(builder.toString()).getDocumentElement();
	}
}
