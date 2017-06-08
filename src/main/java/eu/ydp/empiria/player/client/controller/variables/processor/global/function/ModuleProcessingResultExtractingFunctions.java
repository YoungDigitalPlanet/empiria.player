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
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;

public class ModuleProcessingResultExtractingFunctions {

    private final Function<DtoModuleProcessingResult, Integer> extractTodoFunction = new Function<DtoModuleProcessingResult, Integer>() {
        @Override
        public Integer apply(DtoModuleProcessingResult processingResult) {
            return processingResult.getConstantVariables().getTodo();
        }
    };

    private final Function<DtoModuleProcessingResult, Integer> extractMistakesFunction = new Function<DtoModuleProcessingResult, Integer>() {
        @Override
        public Integer apply(DtoModuleProcessingResult processingResult) {
            return processingResult.getUserInteractionVariables().getMistakes();
        }
    };

    private final Function<DtoModuleProcessingResult, Integer> extractErrorsFunction = new Function<DtoModuleProcessingResult, Integer>() {
        @Override
        public Integer apply(DtoModuleProcessingResult processingResult) {
            return processingResult.getGeneralVariables().getErrors();
        }
    };

    private final Function<DtoModuleProcessingResult, Integer> extractDoneFunction = new Function<DtoModuleProcessingResult, Integer>() {
        @Override
        public Integer apply(DtoModuleProcessingResult processingResult) {
            return processingResult.getGeneralVariables().getDone();
        }
    };

    private final Function<DtoModuleProcessingResult, LastMistaken> extractLastMistakenFunction = new Function<DtoModuleProcessingResult, LastMistaken>() {
        @Override
        public LastMistaken apply(DtoModuleProcessingResult processingResult) {
            return processingResult.getUserInteractionVariables().getLastmistaken();
        }
    };

    public Function<DtoModuleProcessingResult, Integer> getExtractTodoFunction() {
        return extractTodoFunction;
    }

    public Function<DtoModuleProcessingResult, Integer> getExtractMistakesFunction() {
        return extractMistakesFunction;
    }

    public Function<DtoModuleProcessingResult, Integer> getExtractErrorsFunction() {
        return extractErrorsFunction;
    }

    public Function<DtoModuleProcessingResult, Integer> getExtractDoneFunction() {
        return extractDoneFunction;
    }

    public Function<DtoModuleProcessingResult, LastMistaken> getExtractLastMistakenFunction() {
        return extractLastMistakenFunction;
    }

}
