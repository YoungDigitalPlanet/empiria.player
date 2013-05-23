package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;

public class ResponseNodeJAXBParserFactoryTest extends AbstractEmpiriaPlayerGWTTestCase {

	public void testParse() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("<responseDeclaration cardinality=\"multiple\" identifier=\"CONNECTION_RESPONSE_1\" checkMode=\"expression\" countMode=\"\" evaluate =\"correct\">");
		builder.append("		<correctResponse>");
		builder.append("			<value forIndex=\"0\"  group=\"x1\" groupMode=\"groupItem\">CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1</value>");
		builder.append("			<value>CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4</value>");
		builder.append("		</correctResponse>");
		builder.append("</responseDeclaration>");

		ResponseBean responseBean = parse(builder.toString());

		assertNotNull(responseBean);

		assertEquals(responseBean.getCardinality(), Cardinality.MULTIPLE);
		assertEquals(responseBean.getCheckMode(), CheckMode.EXPRESSION);
		assertEquals(responseBean.getEvaluate(), Evaluate.CORRECT);
		assertEquals(responseBean.getIdentifier(), "CONNECTION_RESPONSE_1");
		assertEquals(2, responseBean.getCorrectResponse().getValues().size());
		assertEquals("0", responseBean.getCorrectResponse().getValues().get(0).getForIndex());
		assertEquals("CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1", responseBean.getCorrectResponse().getValues().get(0).getValue());
		assertEquals("x1", responseBean.getCorrectResponse().getValues().get(0).getGroup());
		assertEquals("groupItem", responseBean.getCorrectResponse().getValues().get(0).getGroupMode());
		assertEquals("CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4", responseBean.getCorrectResponse().getValues().get(1).getValue());
		assertEquals(null, responseBean.getCorrectResponse().getValues().get(1).getForIndex());
		assertEquals(null, responseBean.getCorrectResponse().getValues().get(1).getGroup());
		assertEquals(null, responseBean.getCorrectResponse().getValues().get(1).getGroupMode());

	}

	public void testParseOldContentWithoutCheckModeAndEvaluate() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("<responseDeclaration cardinality=\"multiple\" identifier=\"CONNECTION_RESPONSE_1\"  countMode=\"\">");
		builder.append("		<correctResponse>");
		builder.append("			<value forIndex=\"0\"  group=\"x1\" groupMode=\"groupItem\">CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1</value>");
		builder.append("			<value>CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4</value>");
		builder.append("		</correctResponse>");
		builder.append("</responseDeclaration>");

		ResponseBean responseBean = parse(builder.toString());

		assertNotNull(responseBean);

		assertEquals(responseBean.getCardinality(), Cardinality.MULTIPLE);
		assertEquals(responseBean.getCheckMode(), CheckMode.DEFAULT);
		assertEquals(responseBean.getEvaluate(), Evaluate.DEFAULT);
		assertEquals(responseBean.getIdentifier(), "CONNECTION_RESPONSE_1");
		assertEquals(2, responseBean.getCorrectResponse().getValues().size());
		assertEquals("0", responseBean.getCorrectResponse().getValues().get(0).getForIndex());
		assertEquals("CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1", responseBean.getCorrectResponse().getValues().get(0).getValue());
		assertEquals("x1", responseBean.getCorrectResponse().getValues().get(0).getGroup());
		assertEquals("groupItem", responseBean.getCorrectResponse().getValues().get(0).getGroupMode());
		assertEquals("CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4", responseBean.getCorrectResponse().getValues().get(1).getValue());
		assertEquals(null, responseBean.getCorrectResponse().getValues().get(1).getForIndex());
		assertEquals(null, responseBean.getCorrectResponse().getValues().get(1).getGroup());
		assertEquals(null, responseBean.getCorrectResponse().getValues().get(1).getGroupMode());

	}

	private ResponseBean parse(String xml) {
		ResponseNodeJAXBParserFactory jaxbParserFactory = GWT.create(ResponseNodeJAXBParserFactory.class);
		JAXBParser<ResponseBean> jaxbParser = jaxbParserFactory.create();
		ResponseBean responseJAXBModel = jaxbParser.parse(xml);
		return responseJAXBModel;
	}

}
