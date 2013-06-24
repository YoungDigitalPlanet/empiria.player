package eu.ydp.empiria.player.client.controller.variables.processor.global;

import static eu.ydp.gwtutil.client.collections.CollectionsUtil.intCollectionSum;

import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.function.ResultVariablesExtractingFunctions;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResultVariables;

public class GlobalVariablesProcessor {

	@Inject
	private ResultVariablesExtractingFunctions extractingFunctions;

	@Inject
	private ResultVariablesConverter converter;

	public GlobalVariables calculateGlobalVariables(Map<String, DtoModuleProcessingResult> modulesProcessingResults, Map<String, Response> responses) {
		Iterable<ResultVariables> resultVariables = converter.convertToResultVariables(modulesProcessingResults, responses);
		int done = calculateSumOfDone(resultVariables);
		int todo = calcuateSumOfTodoVariables(resultVariables);
		int mistakes = calculateSumOfMistakes(resultVariables);
		int errors = calculateSumOfErrors(resultVariables);

		boolean lastmistaken = checkIfLastmistakenInAnyModule(resultVariables);

		return new GlobalVariables(todo, done, errors, mistakes, lastmistaken);
	}

	private int calculateSumOfDone(Iterable<ResultVariables> modulesProcessingResults) {
		Function<ResultVariables, Integer> extractDoneFunction = extractingFunctions.getExtractDoneFunction();
		int doneSum = calculateSumOfIterables(modulesProcessingResults, extractDoneFunction);
		return doneSum;
	}

	private int calcuateSumOfTodoVariables(Iterable<ResultVariables> modulesProcessingResults) {
		Function<ResultVariables, Integer> extractTodoFunction = extractingFunctions.getExtractTodoFunction();
		int todoSum = calculateSumOfIterables(modulesProcessingResults, extractTodoFunction);
		return todoSum;
	}

	private int calculateSumOfMistakes(Iterable<ResultVariables> modulesProcessingResults) {
		Function<ResultVariables, Integer> extractMistakesFunction = extractingFunctions.getExtractMistakesFunction();
		int mistakesSum = calculateSumOfIterables(modulesProcessingResults, extractMistakesFunction);
		return mistakesSum;
	}

	private int calculateSumOfErrors(Iterable<ResultVariables> modulesProcessingResults) {
		Function<ResultVariables, Integer> extractErrorsFunction = extractingFunctions.getExtractErrorsFunction();
		int errorsSum = calculateSumOfIterables(modulesProcessingResults, extractErrorsFunction);
		return errorsSum;
	}

	private boolean checkIfLastmistakenInAnyModule(Iterable<ResultVariables> results) {
		for (ResultVariables result : results) {
			boolean lastmistakenOfModule = result.isLastMistaken();
			if (lastmistakenOfModule) {
				return true;
			}
		}
		return false;
	}

	private int calculateSumOfIterables(Iterable<ResultVariables> modulesProcessingResults, Function<ResultVariables, Integer> extractValueFunction) {
		Iterable<Integer> extractedValues = Iterables.transform(modulesProcessingResults, extractValueFunction);
		int sumOfValues = intCollectionSum(extractedValues);
		return sumOfValues;
	}
}
