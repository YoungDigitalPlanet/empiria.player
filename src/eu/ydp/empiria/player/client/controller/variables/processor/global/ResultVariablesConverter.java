package eu.ydp.empiria.player.client.controller.variables.processor.global;

import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Maps.filterEntries;
import static com.google.common.collect.Maps.transformEntries;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.filter.DefaultCheckModeFilter;
import eu.ydp.empiria.player.client.controller.variables.processor.global.filter.ExpressionCheckModeFilter;
import eu.ydp.empiria.player.client.controller.variables.processor.global.transformation.DefaultResultVariablesTransformation;
import eu.ydp.empiria.player.client.controller.variables.processor.global.transformation.ExpressionBeanResultsToResultVariableTransformation;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResultVariables;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ResultVariablesConverter {

	@Inject
	private DefaultResultVariablesTransformation defaultResultVaribalesTransformation;

	@Inject
	private ExpressionCheckModeFilter expressionCheckModeFilter;

	@Inject
	private DefaultCheckModeFilter defaultCheckModeFilter;

	@Inject
	private ExpressionBeanResultsToResultVariableTransformation expressionBeanToResultVariableTransformation;

	public Iterable<ResultVariables> convertToResultVariables(Map<String, DtoModuleProcessingResult> modulesProcessingResults, Map<String, Response> responses) {
		Map<Response, DtoModuleProcessingResult> combined = combineToResponseResultMap(modulesProcessingResults, responses);

		Iterable<ResultVariables> defaultCheckModeResults = findResultVariablesForDefaultCheckModeResponses(combined);
		Iterable<ResultVariables> expressionCheckModeResults = findResultVariablesForExpressionCheckModeResponses(combined);

		return concat(defaultCheckModeResults, expressionCheckModeResults);
	}

	private Iterable<ResultVariables> findResultVariablesForExpressionCheckModeResponses(Map<Response, DtoModuleProcessingResult> combined) {
		Map<Response, DtoModuleProcessingResult> expressionCheckModeResponses = filterEntries(combined, expressionCheckModeFilter);
		Map<ExpressionBean, Collection<DtoModuleProcessingResult>> expressionsResults = transformToExpressionResultsMap(expressionCheckModeResponses);
		Map<ExpressionBean, ResultVariables> results = transformEntries(expressionsResults, expressionBeanToResultVariableTransformation);
		return results.values();
	}

	private Iterable<ResultVariables> findResultVariablesForDefaultCheckModeResponses(Map<Response, DtoModuleProcessingResult> combined) {
		Map<Response, DtoModuleProcessingResult> defaultCheckModeResponses = filterEntries(combined, defaultCheckModeFilter);
		return transform(defaultCheckModeResponses.entrySet(), defaultResultVaribalesTransformation);
	}

	private Map<Response, DtoModuleProcessingResult> combineToResponseResultMap(Map<String, DtoModuleProcessingResult> modulesProcessingResults,
			Map<String, Response> responses) {
		Map<Response, DtoModuleProcessingResult> combined = Maps.newHashMap();
		for (String id : responses.keySet()) {
			combined.put(responses.get(id), modulesProcessingResults.get(id));
		}
		return combined;
	}

	private Map<ExpressionBean, Collection<DtoModuleProcessingResult>> transformToExpressionResultsMap(Map<Response, DtoModuleProcessingResult> combined) {
		Map<ExpressionBean, Collection<DtoModuleProcessingResult>> transformed = Maps.newHashMap();
		for (Response response : combined.keySet()) {
			ExpressionBean expression = response.getExpression();
			if (!transformed.containsKey(expression)) {
				transformed.put(expression, Lists.<DtoModuleProcessingResult> newArrayList());
			}
			transformed.get(expression).add(combined.get(response));
		}
		return transformed;
	}
}
