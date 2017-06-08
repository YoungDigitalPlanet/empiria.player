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

package eu.ydp.empiria.player.client.controller.variables.processor.global.function;

import com.google.common.base.Function;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResultVariables;

public class ResultVariablesExtractingFunctions {

    private final Function<ResultVariables, Integer> extractTodoFunction = new Function<ResultVariables, Integer>() {
        @Override
        public Integer apply(ResultVariables processingResult) {
            return processingResult.getTodo();
        }
    };

    private final Function<ResultVariables, Integer> extractMistakesFunction = new Function<ResultVariables, Integer>() {
        @Override
        public Integer apply(ResultVariables processingResult) {
            return processingResult.getMistakes();
        }
    };

    private final Function<ResultVariables, Integer> extractErrorsFunction = new Function<ResultVariables, Integer>() {
        @Override
        public Integer apply(ResultVariables processingResult) {
            return processingResult.getErrors();
        }
    };

    private final Function<ResultVariables, Integer> extractDoneFunction = new Function<ResultVariables, Integer>() {
        @Override
        public Integer apply(ResultVariables processingResult) {
            return processingResult.getDone();
        }
    };

    private final Function<ResultVariables, LastMistaken> extractLastMistakenFunction = new Function<ResultVariables, LastMistaken>() {
        @Override
        public LastMistaken apply(ResultVariables processingResult) {
            return processingResult.getLastMistaken();
        }
    };

    public Function<ResultVariables, Integer> getExtractTodoFunction() {
        return extractTodoFunction;
    }

    public Function<ResultVariables, Integer> getExtractMistakesFunction() {
        return extractMistakesFunction;
    }

    public Function<ResultVariables, Integer> getExtractErrorsFunction() {
        return extractErrorsFunction;
    }

    public Function<ResultVariables, Integer> getExtractDoneFunction() {
        return extractDoneFunction;
    }

    public Function<ResultVariables, LastMistaken> getExtractLastMistakenFunction() {
        return extractLastMistakenFunction;
    }
}
