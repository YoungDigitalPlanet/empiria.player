/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller.variables.processor.global;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.function.ResultVariablesExtractingFunctions;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResultVariables;

import java.util.Map;

import static eu.ydp.gwtutil.client.collections.CollectionsUtil.intCollectionSum;

public class GlobalVariablesProcessor {

    @Inject
    private ResultVariablesExtractingFunctions extractingFunctions;

    @Inject
    private ResultVariablesConverter converter;

    public GlobalVariables calculateGlobalVariables(Map<String, DtoModuleProcessingResult> modulesProcessingResults, VariableManager<Response> responseManager) {
        Iterable<ResultVariables> resultVariables = converter.convertToResultVariables(modulesProcessingResults, responseManager);
        int done = calculateSumOfDone(resultVariables);
        int todo = calculateSumOfTodoVariables(resultVariables);
        int mistakes = calculateSumOfMistakes(resultVariables);
        int errors = calculateSumOfErrors(resultVariables);

        LastMistaken lastmistaken = findLastmistakenForModules(resultVariables);

        return new GlobalVariables(todo, done, errors, mistakes, lastmistaken);
    }

    private int calculateSumOfDone(Iterable<ResultVariables> modulesProcessingResults) {
        Function<ResultVariables, Integer> extractDoneFunction = extractingFunctions.getExtractDoneFunction();
        int doneSum = calculateSumOfIterables(modulesProcessingResults, extractDoneFunction);
        return doneSum;
    }

    private int calculateSumOfTodoVariables(Iterable<ResultVariables> modulesProcessingResults) {
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

    private LastMistaken findLastmistakenForModules(Iterable<ResultVariables> results) {
        if (containsAnyResultWithGivenLastMistakenType(results, LastMistaken.WRONG)) {
            return LastMistaken.WRONG;
        } else if (containsAnyResultWithGivenLastMistakenType(results, LastMistaken.CORRECT)) {
            return LastMistaken.CORRECT;
        } else {
            return LastMistaken.NONE;
        }
    }

    private boolean containsAnyResultWithGivenLastMistakenType(Iterable<ResultVariables> results, LastMistaken searchedLastMistaken) {
        for (ResultVariables resultVariables : results) {
            LastMistaken currentLastMistaken = resultVariables.getLastMistaken();
            if (searchedLastMistaken == currentLastMistaken) {
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
