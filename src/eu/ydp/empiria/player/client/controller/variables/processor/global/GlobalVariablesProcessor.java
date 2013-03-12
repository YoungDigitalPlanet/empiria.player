package eu.ydp.empiria.player.client.controller.variables.processor.global;

import java.util.Collection;

import com.google.gwt.thirdparty.guava.common.base.Function;
import com.google.gwt.thirdparty.guava.common.collect.Iterables;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;

public class GlobalVariablesProcessor {

	private ExtractingFunctionsProvider extractingFunctionsProvider;
	
	@Inject
	public GlobalVariablesProcessor(ExtractingFunctionsProvider extractingFunctionsProvider) {
		this.extractingFunctionsProvider = extractingFunctionsProvider;
	}

	public GlobalVariables calculateGlobalVariables(Collection<DtoModuleProcessingResult> modulesProcessingResults){
		int done = calculateSumOfDone(modulesProcessingResults);
		int todo = calcuateSumOfTodoVariables(modulesProcessingResults);
		int mistakes = calculateSumOfMistakes(modulesProcessingResults);
		int errors = calculateSumOfErrors(modulesProcessingResults);
		
		boolean lastmistaken = checkIfLastmistakenInAnyModule(modulesProcessingResults);
		
		GlobalVariables globalVariables = new GlobalVariables(todo, errors, done, lastmistaken, mistakes);
		return globalVariables;
	}

	private int calculateSumOfDone(Collection<DtoModuleProcessingResult> modulesProcessingResults) {
		Function<DtoModuleProcessingResult, Integer> extractDoneFunction = extractingFunctionsProvider.getExtractDoneFunction();
		int sumOfDone = calculateSumOfIterables(modulesProcessingResults, extractDoneFunction);
		return sumOfDone;
	}

	private int calcuateSumOfTodoVariables(Collection<DtoModuleProcessingResult> modulesProcessingResults) {
		Function<DtoModuleProcessingResult, Integer> extractTodoFunction = extractingFunctionsProvider.getExtractTodoFunction();
		int sumOfTodo = calculateSumOfIterables(modulesProcessingResults, extractTodoFunction);
		return sumOfTodo;
	}

	private int calculateSumOfMistakes(Collection<DtoModuleProcessingResult> modulesProcessingResults) {
		Function<DtoModuleProcessingResult, Integer> extractMistakesFunction = extractingFunctionsProvider.getExtractMistakesFunction();
		int sumOfMistakes = calculateSumOfIterables(modulesProcessingResults, extractMistakesFunction);
		return sumOfMistakes;
	}
	
	private int calculateSumOfErrors(Collection<DtoModuleProcessingResult> modulesProcessingResults) {
		Function<DtoModuleProcessingResult, Integer> extractErrorsFunction = extractingFunctionsProvider.getExtractErrorsFunction();
		int sumOfErrors = calculateSumOfIterables(modulesProcessingResults, extractErrorsFunction);
		return sumOfErrors;
	}
	
	private boolean checkIfLastmistakenInAnyModule(Collection<DtoModuleProcessingResult> modulesProcessingResults) {
		for (DtoModuleProcessingResult dtoProcessingResult : modulesProcessingResults) {
			boolean lastmistakenOfModule = extractLastmistakenOfModule(dtoProcessingResult);
			if(lastmistakenOfModule){
				return true;
			}
		}
		return false;
	}

	private boolean extractLastmistakenOfModule(DtoModuleProcessingResult dtoProcessingResult) {
		return dtoProcessingResult.getUserInteractionVariables().isLastmistaken();
	}

	private int calculateSumOfIterables(Collection<DtoModuleProcessingResult> modulesProcessingResults, Function<DtoModuleProcessingResult, Integer> extractValueFunction) {
		Iterable<Integer> extractedValues = Iterables.transform(modulesProcessingResults, extractValueFunction);
		int sumOfValues = sumVariables(extractedValues);
		return sumOfValues;
	}

	private int sumVariables(Iterable<Integer> modulesTodoVariables) {
		int sum = 0;
		for (Integer value : modulesTodoVariables) {
			sum += value;
		}
		return sum;
	}
}
