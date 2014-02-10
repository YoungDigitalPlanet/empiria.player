package eu.ydp.empiria.player.client.module.expression.model;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.expression.ExpressionMode;

@SuppressWarnings("PMD")
public class ExpressionModuleJAXBParserFactoryGWTTest extends AbstractEmpiriaPlayerGWTTestCase {

	public void testParseExpressionsWithCDATA() throws Exception {
		final String xml = "<expressions>" + "<expression mode=\"commutation\">" + "<![CDATA['a'+'b'=6]]>" + "</expression>" + "<expression mode=\"default\">"
				+ "<![CDATA[3'sign'5=15]]>" + "</expression>" + "<expression>" + "<![CDATA[3'sign'5=15]]>" + "</expression>" + "</expressions>";

		ExpressionsBean expressionsBean = parse(xml);
		assertNotNull(expressionsBean);
		List<ExpressionBean> expressions = expressionsBean.getExpressions();
		assertNotNull(expressions);
		assertEquals(3, expressionsBean.getExpressions().size());

		ExpressionBean firstExpression = expressions.get(0);
		assertEquals("'a'+'b'=6", firstExpression.getTemplate());
		assertEquals(0, firstExpression.getResponses().size());
		assertEquals(ExpressionMode.COMMUTATION, firstExpression.getMode());

		ExpressionBean secondExpression = expressions.get(1);
		assertEquals("3'sign'5=15", secondExpression.getTemplate());
		assertEquals(0, secondExpression.getResponses().size());
		assertEquals(ExpressionMode.DEFAULT, secondExpression.getMode());

		ExpressionBean thirdExpression = expressions.get(2);
		assertEquals("3'sign'5=15", thirdExpression.getTemplate());
		assertEquals(0, thirdExpression.getResponses().size());
		assertEquals(ExpressionMode.DEFAULT, thirdExpression.getMode());
	}

	private ExpressionsBean parse(String xml) {
		ExpressionModuleJAXBParserFactory jaxbParserFactory = GWT.create(ExpressionModuleJAXBParserFactory.class);
		JAXBParser<ExpressionsBean> jaxbParser = jaxbParserFactory.create();
		ExpressionsBean expressionsBean = jaxbParser.parse(xml);
		return expressionsBean;
	}

}
