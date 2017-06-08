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
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Words;

import java.util.List;
import java.util.Map;

public class WordFinder {

    @Inject
    private Provider<WordsResultFinder> finderProvider;

    @Inject
    private FirstWordFinder firstWordFinder;

    public Optional<WordsResult> getWordsResult(String text, Words words) {
        Map<String, Integer> baseIndexes = words.getBaseIndexes();

        if (Strings.isNullOrEmpty(text)) {
            return firstWordFinder.find(words);
        }

        String lowerCaseText = text.toLowerCase();

        String firstLetter = getFirstLetter(lowerCaseText);
        List<String> currentWords = words.getWordsByLetter(firstLetter);

        if (currentWords == null) {
            return Optional.absent();
        }

        if (hasOnlyOneLetter(lowerCaseText)) {

            int index = baseIndexes.get(lowerCaseText);
            WordsResult foundWords = new WordsResult(currentWords, index);
            return Optional.of(foundWords);
        }

        WordsResultFinder finder = finderProvider.get();
        WordsResult foundWords = finder.findPhrasesMatchingPrefix(currentWords, baseIndexes, lowerCaseText);
        return Optional.of(foundWords);
    }

    private boolean hasOnlyOneLetter(String lowerCaseText) {
        return lowerCaseText.length() == 1;
    }

    private String getFirstLetter(String lowerCaseText) {
        return lowerCaseText.substring(0, 1);
    }
}
