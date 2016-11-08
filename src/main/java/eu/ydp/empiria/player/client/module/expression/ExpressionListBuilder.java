package eu.ydp.empiria.player.client.module.expression;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionsBean;

import java.util.List;
import java.util.Map;

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

    public List<ExpressionBean> parseAndConnectExpressions(String expressionsXml, ItemResponseManager responseManager) {
        List<ExpressionBean> expressions = parseExpressions(expressionsXml);
        connectResponsesToExpressions(expressions, responseManager);

        return expressions;
    }

    private void connectResponsesToExpressions(List<ExpressionBean> expressions, ItemResponseManager responseManager) {
        for (ExpressionBean expressionBean : expressions) {
            expressionToResponseConnector.connectResponsesToExpression(expressionBean, responseManager);

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
