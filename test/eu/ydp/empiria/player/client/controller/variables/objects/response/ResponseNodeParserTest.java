package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.google.gwt.core.client.GWT;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;

public class ResponseNodeParserTest extends AbstractEmpiriaPlayerGWTTestCase {

	private ResponseNodeParser responseNodeParser;

	public void testParseResponseFromNodeTest() {
		ResponseNodeJAXBParserFactory responseNodeJAXBParserFactory = GWT.create(ResponseNodeJAXBParserFactory.class);

		responseNodeParser = new ResponseNodeParser(responseNodeJAXBParserFactory, new ResponseBeanConverter());

		Response response = responseNodeParser.parseResponseFromNode(getResponseDeclaration());

		assertEquals(response.cardinality, Cardinality.MULTIPLE);
		assertEquals(response.identifier, "CONNECTION_RESPONSE_1");
		assertEquals(response.evaluate, Evaluate.CORRECT);
		assertEquals(response.cardinality, Cardinality.MULTIPLE);
		assertEquals(response.getCheckMode(), CheckMode.EXPRESSION);
		assertEquals(3, response.correctAnswers.getAllAnswers().size());
		assertEquals(response.correctAnswers.getAllAnswers().get(0), "CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1");
		assertEquals(response.correctAnswers.getAllAnswers().get(1), "CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_2");
		assertEquals(response.correctAnswers.getAllAnswers().get(2), "CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4");
		assertEquals(response.getID(), "CONNECTION_RESPONSE_1"); // checkMode
		assertEquals(response.getCountMode(), CountMode.SINGLE);
		assertEquals(response.groups.get(0), "x1");
		assertEquals(response.groups.get(1), "x2");
	}

	private String getResponseDeclaration() {
		StringBuilder builder = new StringBuilder();
		builder.append("<responseDeclaration cardinality=\"multiple\" identifier=\"CONNECTION_RESPONSE_1\" checkMode=\"expression\" countMode=\"\" evaluate =\"correct\">");
		builder.append("		<correctResponse>");
		builder.append("			<value forIndex=\"0\"  group=\"x1\" groupMode=\"groupItem\">CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1</value>");
		builder.append("			<value forIndex=\"0\"  group=\"x2\" groupMode=\"groupItem\">CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_2</value>");
		builder.append("			<value>CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4</value>");
		builder.append("		</correctResponse>");
		builder.append("</responseDeclaration>");

		return builder.toString();
	}
}
