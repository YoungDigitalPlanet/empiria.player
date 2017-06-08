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

package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResponseBuilder {

    private Evaluate evaluate = Evaluate.USER;
    private CorrectAnswers correctAnswers = new CorrectAnswers();
    private List<String> values = new ArrayList<String>();
    private List<String> groups = new ArrayList<String>();
    private String identifier = "defaultIdentifier";
    private Cardinality cardinality;
    private CountMode countMode = CountMode.SINGLE;
    private ExpressionBean expression = null;
    private CheckMode checkMode = CheckMode.DEFAULT;
    private CountMode compilerCountMode;

    public ResponseBuilder withEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
        return this;
    }

    public ResponseBuilder withCorrectAnswers(CorrectAnswers correctAnswers) {
        this.correctAnswers = correctAnswers;
        return this;
    }

    public ResponseBuilder withValues(List<String> values) {
        this.values = values;
        return this;
    }

    public ResponseBuilder withGroups(List<String> groups) {
        this.groups = groups;
        return this;
    }

    public ResponseBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public ResponseBuilder withCardinality(Cardinality cardinality) {
        this.cardinality = cardinality;
        return this;
    }

    public ResponseBuilder withCorrectAnswers(Iterable<String> correctAnswers) {
        CorrectAnswers correctAnswersHolder = new CorrectAnswers();
        for (String correctAnswer : correctAnswers) {
            ResponseValue responseValue = new ResponseValue(correctAnswer);
            correctAnswersHolder.add(responseValue);
        }
        this.correctAnswers = correctAnswersHolder;
        return this;
    }

    public ResponseBuilder withCorrectAnswers(String... correctAnswers) {
        return withCorrectAnswers(Lists.newArrayList(correctAnswers));
    }

    public ResponseBuilder withCurrentUserAnswers(String... currentUserAnswers) {
        return withCurrentUserAnswers(Arrays.asList(currentUserAnswers));
    }

    public ResponseBuilder withCurrentUserAnswers(Iterable<String> currentUserAnswers) {
        this.values = Lists.newArrayList(currentUserAnswers);
        return this;
    }

    public ResponseBuilder withGroups(String... groups) {
        this.groups = Arrays.asList(groups);
        return this;
    }

    public ResponseBuilder withCountMode(CountMode countMode) {
        this.countMode = countMode;
        return this;
    }

    public ResponseBuilder withExpression(ExpressionBean expression) {
        this.expression = expression;
        return this;
    }

    public ResponseBuilder withCheckMode(CheckMode checkMode) {
        this.checkMode = checkMode;
        return this;
    }

    public ResponseBuilder withCompilerCountMode(CountMode compilerCountMode) {
        this.compilerCountMode = compilerCountMode;
        return this;
    }

    public Response build() {
        return new Response(correctAnswers, values, groups, identifier, evaluate, cardinality, countMode, expression, checkMode, compilerCountMode);
    }
}
