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

package eu.ydp.empiria.player.client.module.expression;

import com.google.common.collect.ImmutableMap;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ExpressionReplacerJUnitTest {

    private ExpressionReplacer expressionReplacer = new ExpressionReplacer();

    @Test
    public void eligibleForReplacement() {
        // given
        expressionReplacer.useReplacements(ImmutableMap.of("a", "b", "c", "dx"));
        final String GIVEN_TEXT = "a";

        // when
        boolean checkResult = expressionReplacer.isEligibleForReplacement(GIVEN_TEXT);
        String replaced = expressionReplacer.replace(GIVEN_TEXT);

        // then
        assertThat(checkResult).isTrue();
        assertThat(replaced).isEqualTo("b");
    }

    @Test
    @Parameters({"x", "ax", "xa", ""})
    public void notEligibleForReplacement(final String givenText) {
        // given
        expressionReplacer.useReplacements(ImmutableMap.of("a", "b", "c", "dx"));

        // when
        boolean checkResult = expressionReplacer.isEligibleForReplacement(givenText);

        // then
        assertThat(checkResult).isFalse();
    }

}
