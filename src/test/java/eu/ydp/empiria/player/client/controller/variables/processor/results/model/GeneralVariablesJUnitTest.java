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

package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class GeneralVariablesJUnitTest {

    @Test
    public void answersModification_collectionShouldBeCopied() {
        // given
        List<String> answers = Lists.newArrayList("a");
        List<Boolean> answersEvaluation = Lists.newArrayList();
        GeneralVariables gv = new GeneralVariables(answers, answersEvaluation, 0, 0);

        // when
        answers.set(0, "b");

        // then
        assertThat(gv.getAnswers().get(0)).isEqualTo("a");

    }

    @Test
    public void answersModification_setByMutator_collectionShouldBeCopied() {
        // given
        GeneralVariables gv = new GeneralVariables();
        List<String> answers = Lists.newArrayList("a");
        gv.setAnswers(answers);

        // when
        answers.set(0, "b");

        // then
        assertThat(gv.getAnswers().get(0)).isEqualTo("a");

    }

    @Test
    public void evaluationModification_collectionShouldBeCopied() {
        // given
        List<String> answers = Lists.newArrayList();
        List<Boolean> answersEvaluation = Lists.newArrayList(true);
        GeneralVariables gv = new GeneralVariables(answers, answersEvaluation, 0, 0);

        // when
        answersEvaluation.set(0, false);

        // then
        assertThat(gv.getAnswersEvaluation().get(0)).isTrue();

    }

    @Test
    public void evaluationModification_setByMutator_collectionShouldBeCopied() {
        // given
        GeneralVariables gv = new GeneralVariables();
        List<Boolean> answersEvaluation = Lists.newArrayList(true);
        gv.setAnswersEvaluation(answersEvaluation);

        // when
        answersEvaluation.set(0, false);

        // then
        assertThat(gv.getAnswersEvaluation().get(0)).isTrue();

    }

}
