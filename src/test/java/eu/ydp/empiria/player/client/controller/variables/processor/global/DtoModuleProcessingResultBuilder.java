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

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.*;

import java.util.List;

public class DtoModuleProcessingResultBuilder {

    private int todo = 0;
    private final List<String> answers = Lists.newArrayList();
    private List<Boolean> answersEvaluation = Lists.newArrayList();
    private int errors = 0;
    private int done = 0;
    private final LastAnswersChanges lastAnswerChanges = new LastAnswersChanges();
    private LastMistaken lastmistaken = LastMistaken.NONE;
    private int mistakes = 0;

    public DtoModuleProcessingResultBuilder withTodo(int todo) {
        this.todo = todo;
        return this;
    }

    public DtoModuleProcessingResultBuilder withErrors(int errors) {
        this.errors = errors;
        return this;
    }

    public DtoModuleProcessingResultBuilder withDone(int done) {
        this.done = done;
        return this;
    }

    public DtoModuleProcessingResultBuilder withMistakes(int mistakes) {
        this.mistakes = mistakes;
        return this;
    }

    public DtoModuleProcessingResultBuilder withLastmistaken(LastMistaken lastmistaken) {
        this.lastmistaken = lastmistaken;
        return this;
    }

    public DtoModuleProcessingResultBuilder withAnswerEvaluations(List<Boolean> answerEvaluations) {
        this.answersEvaluation = answerEvaluations;
        return this;
    }

    public DtoModuleProcessingResult build() {
        GeneralVariables generalVariables = new GeneralVariables(answers, answersEvaluation, errors, done);
        ConstantVariables constantVariables = new ConstantVariables(todo);
        UserInteractionVariables userInteractionVariables = new UserInteractionVariables(lastAnswerChanges, lastmistaken, mistakes);
        DtoModuleProcessingResult dtoModuleProcessingResult = new DtoModuleProcessingResult(generalVariables, constantVariables, userInteractionVariables);
        return dtoModuleProcessingResult;
    }
}
