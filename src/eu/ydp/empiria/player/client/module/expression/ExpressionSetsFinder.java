package eu.ydp.empiria.player.client.module.expression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ExpressionSetsFinder {

	private final IdentifiersFromExpressionExtractor identifiersFromExpressionExtractor;
	private final ExpressionToPartsDivider expressionToPartsDivider;
	
	@Inject
	public ExpressionSetsFinder(
			IdentifiersFromExpressionExtractor identifiersFromExpressionExtractor,
			ExpressionToPartsDivider expressionToPartsDivider) {
		this.identifiersFromExpressionExtractor = identifiersFromExpressionExtractor;
		this.expressionToPartsDivider = expressionToPartsDivider;
	}

	public void updateResponsesSetsInExpression(ExpressionBean expression){
		String template = expression.getTemplate();
		List<Response> responses = expression.getResponses();
		
		List<String> leftRightParts = expressionToPartsDivider.divideExpressionOnEquality(template, responses);
		List<String> allExpressionParts = new ArrayList<String>();
		allExpressionParts.add(template);
		allExpressionParts.addAll(leftRightParts);
		
		List<Set<Response>> setsOfResponses = findSetsOfResponsesByExpressionParts(allExpressionParts, responses);
		expression.setSetsOfResponses(setsOfResponses);
	}

	private List<Set<Response>> findSetsOfResponsesByExpressionParts(List<String> allExpressionParts, List<Response> responses) {
		List<Set<Response>> setsOfResponses = new ArrayList<Set<Response>>();
		for(String expressionPart : allExpressionParts){
			List<String> responseIdentifiers = identifiersFromExpressionExtractor.extractResponseIdentifiersFromTemplate(expressionPart);
			Set<Response> responseRelatedToExpressionPart = getResponsesByIds(responseIdentifiers, responses);
			setsOfResponses.add(responseRelatedToExpressionPart);
		}
		
		return setsOfResponses;
	}

	private Set<Response> getResponsesByIds(List<String> responseIdentifiers, List<Response> responses) {
		Set<Response> fittingResponses = new HashSet<Response>();
		for(Response response : responses){
			String responseId = response.getID();
			if(responseIdentifiers.contains(responseId)){
				fittingResponses.add(response);
			}
		}
		return fittingResponses;
	}
}
