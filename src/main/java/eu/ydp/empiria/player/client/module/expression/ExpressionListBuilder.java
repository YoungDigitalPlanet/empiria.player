package eu.ydp.empiria.player.client.module.expression;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionsBean;

public class ExpressionListBuilder {

	private final ExpressionModuleJAXBParserFactory jaxbParserFactory;
	private final ExpressionToResponseConnector expressionToResponseConnector;
	private final ExpressionSetsFinder expressionSetsFinder;

	@Inject
	public ExpressionListBuilder(ExpressionModuleJAXBParserFactory jaxbParserFactory, ExpressionToResponseConnector expressionToResponseConnector,
			ExpressionSetsFinder expressionSetsFinder) {
		this.jaxbParserFactory = jaxbParserFactory;
		this.expressionToResponseConnector = expressionToResponseConnector;
		this.expressionSetsFinder = expressionSetsFinder;
	}

	public List<ExpressionBean> parseAndConnectExpressions(String expressionsXml, Map<String, Response> responses) {
		List<ExpressionBean> expressions = parseExpressions(expressionsXml);
		connectResponsesToExpressions(expressions, responses);

		return expressions;
	}

	private void connectResponsesToExpressions(List<ExpressionBean> expressions, Map<String, Response> responses) {
		for (ExpressionBean expressionBean : expressions) {
			expressionToResponseConnector.connectResponsesToExpression(expressionBean, responses);

			if (expressionBean.getMode() == ExpressionMode.COMMUTATION) {
				expressionSetsFinder.updateResponsesSetsInExpression(expressionBean);
			}
		}
	}

	private List<ExpressionBean> parseExpressions(String expressionsXml) {
		JAXBParser<ExpressionsBean> jaxbParser = jaxbParserFactory.create();
		ExpressionsBean expressionsBean = jaxbParser.parse(expressionsXml);
		return expressionsBean.getExpressions();
	}

}
