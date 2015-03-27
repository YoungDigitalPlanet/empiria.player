package eu.ydp.empiria.player.client.controller.item;

import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.expression.ExpressionListBuilder;

public class ItemExpressionParser {
	@Inject
	private ExpressionListBuilder expressionListBuilder;

	@Inject
	@PageScoped
	private ItemXMLWrapper xmlMapper;

	@Inject
	@PageScoped
	private ItemResponseManager responseManager;

	public void parseAndConnectExpressions() {
		NodeList expressionsNodes = xmlMapper.getExpressions();
		Map<String, Response> responsesMap = responseManager.getVariablesMap();
		for (int i = 0; i < expressionsNodes.getLength(); i++) {
			Element expressionsElement = (Element) expressionsNodes.item(i);
			String expressionsXml = expressionsElement.toString();
			expressionListBuilder.parseAndConnectExpressions(expressionsXml, responsesMap);
		}
	}
}
