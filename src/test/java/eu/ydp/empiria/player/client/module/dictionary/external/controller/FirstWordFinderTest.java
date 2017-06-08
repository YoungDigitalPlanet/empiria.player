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

package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Words;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import static org.fest.assertions.api.Assertions.assertThat;

public class FirstWordFinderTest {

    private final LinkedHashMap<String, List<String>> wordsByLetter = Maps.newLinkedHashMap();
    private final TreeMap<String, Integer> baseIndexes = Maps.newTreeMap();
    private final FirstWordFinder testObj = new FirstWordFinder();

    @Test
    public void shouldReturnAbsent_whenWordsAreEmpty() {
        // given
        Words words = new Words(wordsByLetter, baseIndexes);

        // when
        Optional<WordsResult> result = testObj.find(words);

        // then
        assertThat(result).isEqualTo(Optional.<WordsResult>absent());
    }

    @Test
    public void shouldReturnPresentInstance_whenWordsAreNotEmpty() {
        // given
        List<String> wordsByLetterK = Lists.newArrayList("ka", "kb");
        wordsByLetter.put("k", wordsByLetterK);
        baseIndexes.put("k", 0);
        List<String> wordsByLetterO = Lists.newArrayList("on", "oz");
        wordsByLetter.put("o", wordsByLetterO);
        baseIndexes.put("o", 1);

        Words words = new Words(wordsByLetter, baseIndexes);

        // when
        Optional<WordsResult> result = testObj.find(words);


        // then
        assertThat(result.isPresent()).isTrue();
        WordsResult wordResult = result.get();
        assertThat(wordResult.getList()).isEqualTo(wordsByLetterK);
        assertThat(wordResult.getIndex()).isEqualTo(0);
    }
}
