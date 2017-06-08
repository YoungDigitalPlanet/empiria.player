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

package eu.ydp.empiria.player.client.module.gap;

import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class GapExpressionReplacerJUnitTest {

    private GapExpressionReplacer replacer;

    @Before
    public void setUp() {
        replacer = Guice.createInjector().getInstance(GapExpressionReplacer.class);
    }

    @Test
    public void ensureReplacement() {
        // given
        final String ORIGINAL_TEXT = "a";
        replacer.useCharacters("a|b|c|dx");

        // when
        String replacedText = replacer.ensureReplacement(ORIGINAL_TEXT);

        // then
        assertThat(replacedText).isEqualTo("b");
    }

    @Test
    public void ensureReplacement_notEligibleForReplacement() {
        // given
        final String ORIGINAL_TEXT = "e";
        replacer.useCharacters("a|b|c|dx");

        // when
        String replacedText = replacer.ensureReplacement(ORIGINAL_TEXT);

        // then
        assertThat(replacedText).isEqualTo("e");
    }

    @Test
    public void ensureReplacement_notEligibleForReplacement_partIsInSet() {
        // given
        final String ORIGINAL_TEXT = "af";
        replacer.useCharacters("a|b|c|dx");

        // when
        String replacedText = replacer.ensureReplacement(ORIGINAL_TEXT);

        // then
        assertThat(replacedText).isEqualTo("af");
    }

    @Test
    public void ensureReplacement_empty() {
        // given
        final String ORIGINAL_TEXT = "";
        replacer.useCharacters("a|b|c|dx");

        // when
        String replacedText = replacer.ensureReplacement(ORIGINAL_TEXT);

        // then
        assertThat(replacedText).isEqualTo("");
    }

    @Test
    public void ensureReplacement_charactersNotSet() {
        // given
        final String ORIGINAL_TEXT = "a";

        // when
        String replacedText = replacer.ensureReplacement(ORIGINAL_TEXT);

        // then
        assertThat(replacedText).isEqualTo("a");
    }
}
