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
import org.junit.Test;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class PipedReplacementsParserJUnitTest {

    private PipedReplacementsParser parser = new PipedReplacementsParser();

    @Test
    public void parse() {
        // given
        String characters = "a|b|>=|≥|x|×|*|×";

        // when
        Map<String, String> map = parser.parse(characters);

        // then
        assertThat(map).isEqualTo(ImmutableMap.of("a", "b", ">=", "≥", "x", "×", "*", "×"));
    }

    @Test
    public void parse_shouldTrimWhitespaces() {
        // given
        String characters = " \n\ta|b|>=|≥|x|×|*|× ";

        // when
        Map<String, String> map = parser.parse(characters);

        // then
        assertThat(map).isEqualTo(ImmutableMap.of("a", "b", ">=", "≥", "x", "×", "*", "×"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_InvalidSet_oddNumberOfParts() {
        // given
        String characters = "a|b|>=|≥|x|×|*";

        // when
        parser.parse(characters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_InvalidSet_emptyPart() {
        // given
        String characters = "a|b|>=|≥|x||*";

        // when
        parser.parse(characters);
    }

    @Test
    public void parse_EmptySet() {
        // given
        String characters = "";

        // when
        parser.parse(characters);
    }
}
