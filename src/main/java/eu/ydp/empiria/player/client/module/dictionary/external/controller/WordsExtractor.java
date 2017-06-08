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

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Words;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WordsExtractor {

    private static final String DICTIONARY_DELIMITER = "#####\n";
    public static final String WORD_DELIMITER = "\n";

    public Words extractWords(String text) {
        LinkedHashMap<String, List<String>> wordsByLetter = splitToWordsMap(text);
        TreeMap<String, Integer> baseIndexes = buildIndexes(wordsByLetter);

        return new Words(wordsByLetter, baseIndexes);
    }

    private LinkedHashMap<String, List<String>> splitToWordsMap(String text) {
        LinkedHashMap<String, List<String>> wordsMap = Maps.newLinkedHashMap();

        Iterable<String> parsedWordsByLetter = Splitter.on(DICTIONARY_DELIMITER).trimResults()
                .omitEmptyStrings().split(text);

        for (String wordsByLetter : parsedWordsByLetter) {
            List<String> words = splitWords(wordsByLetter);
            addWords(words, wordsMap);
        }
        return wordsMap;
    }

    private TreeMap<String, Integer> buildIndexes(Map<String, List<String>> passwordsByLetter) {
        TreeMap<String, Integer> baseIndexes = Maps.newTreeMap();
        int indexSum = 0;
        for (List<String> currList : passwordsByLetter.values()) {
            String firstLetter = extractFirstWordsFirstLetter(currList);
            baseIndexes.put(firstLetter, indexSum);
            indexSum += currList.size();
        }

        return baseIndexes;
    }

    private List<String> splitWords(String wordsByLetter) {
        Iterable<String> words = Splitter.on(WORD_DELIMITER).split(wordsByLetter);
        return Lists.newArrayList(words);
    }

    private void addWords(List<String> words, Map<String, List<String>> passwordsByLetter) {
        String firstLetter = extractFirstWordsFirstLetter(words);
        passwordsByLetter.put(firstLetter, words);
    }

    private String extractFirstWordsFirstLetter(List<String> currList) {
        String firstWord = currList.get(0);
        return firstWord.substring(0, 1).toLowerCase();
    }

}
