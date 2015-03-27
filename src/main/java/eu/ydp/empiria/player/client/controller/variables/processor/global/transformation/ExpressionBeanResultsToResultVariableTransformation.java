package eu.ydp.empiria.player.client.controller.variables.processor.global.transformation;

import static eu.ydp.gwtutil.client.collections.CollectionsUtil.intCollectionSum;

import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps.EntryTransformer;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.global.function.ModuleProcessingResultExtractingFunctions;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResultVariables;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ExpressionBeanResultsToResultVariableTransformation implements
		EntryTransformer<ExpressionBean, Collection<DtoModuleProcessingResult>, ResultVariables> {

	private static final int EXPRESSION_TODO = 1;

	@Inject
	private ModuleProcessingResultExtractingFunctions extractingFunctionsProvider;

	@Override
	public ResultVariables transformEntry(@Nullable ExpressionBean key, @Nullable Collection<DtoModuleProcessingResult> values) {
		int todo = findTodo();
		int done = findDone(values);
		int errors = findErrors(values);
		int mistakes = findMistakes(values);
		LastMistaken lastMistaken = findLastMistaken(values);
		return new GlobalVariables(todo, done, errors, mistakes, lastMistaken);
	}

	private int findTodo() {
		return EXPRESSION_TODO;
	}

	private int findDone(Collection<DtoModuleProcessingResult> results) {
		Function<DtoModuleProcessingResult, Integer> extractDoneFunction = extractingFunctionsProvider.getExtractDoneFunction();
		Iterable<Integer> dones = Iterables.transform(results, extractDoneFunction);
		boolean atLeastOneIsNotDone = Iterables.contains(dones, 0);
		return atLeastOneIsNotDone ? 0 : EXPRESSION_TODO;
	}

	private int findMistakes(Collection<DtoModuleProcessingResult> results) {
		Function<DtoModuleProcessingResult, Integer> extractMistakesFunction = extractingFunctionsProvider.getExtractMistakesFunction();
		int sumOfMistakes = extractFromIterable(results, extractMistakesFunction);
		return sumOfMistakes;
	}

	private int findErrors(Collection<DtoModuleProcessingResult> results) {
		Function<DtoModuleProcessingResult, Integer> extractErrorsFunction = extractingFunctionsProvider.getExtractErrorsFunction();
		Iterable<Integer> errors = Iterables.transform(results, extractErrorsFunction);
		boolean atLeastOneError = Iterables.contains(errors, 1);
		return atLeastOneError ? EXPRESSION_TODO : 0;
	}

	private LastMistaken findLastMistaken(Collection<DtoModuleProcessingResult> results) {
		Function<DtoModuleProcessingResult, LastMistaken> extractLastMistakenFunction = extractingFunctionsProvider.getExtractLastMistakenFunction();
		Iterable<LastMistaken> lastMistakens = Iterables.transform(results, extractLastMistakenFunction);
		boolean containsAnyWrong = Iterables.contains(lastMistakens, LastMistaken.WRONG);
		boolean containsAnyCorrect = Iterables.contains(lastMistakens, LastMistaken.CORRECT);
		if (containsAnyWrong) {
			return LastMistaken.WRONG;
		} else if (containsAnyCorrect) {
			return LastMistaken.CORRECT;
		} else {
			return LastMistaken.NONE;
		}
	}

	private int extractFromIterable(Iterable<DtoModuleProcessingResult> modulesProcessingResults,
			Function<DtoModuleProcessingResult, Integer> extractValueFunction) {
		Iterable<Integer> extractedValues = Iterables.transform(modulesProcessingResults, extractValueFunction);
		int sumOfValues = intCollectionSum(extractedValues);
		return sumOfValues;
	}

}
