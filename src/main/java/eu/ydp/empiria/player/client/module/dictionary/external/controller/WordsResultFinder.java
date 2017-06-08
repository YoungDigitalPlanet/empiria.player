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

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.search.BinarySearchBestMatch;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.search.LinearSearch;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.search.StringIgnoreCaseComparator;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.search.StringLettersMatchComparator;

import java.util.List;
import java.util.Map;

public class WordsResultFinder {

    private final BinarySearchBestMatch<String> bestMatch;
    private final LinearSearch<String> linearSearch;

    @Inject
    public WordsResultFinder(BinarySearchBestMatch<String> bestMatch, LinearSearch<String> linearSearch) {
        this.bestMatch = bestMatch;
        this.linearSearch = linearSearch;
    }

    public WordsResult findPhrasesMatchingPrefix(List<String> wordsList, Map<String, Integer> baseIndexes, String prefix) {

        int index = bestMatch.search(wordsList, prefix, new StringIgnoreCaseComparator());

        int indexAfterLinear = linearSearch.search(wordsList, prefix, index, new StringLettersMatchComparator());

        return generateWordsResult(wordsList, baseIndexes, indexAfterLinear);
    }

    private WordsResult generateWordsResult(List<String> wordsList, Map<String, Integer> baseIndexes, int indexAfterLinear) {

        int indexInWholeDictionary = calculateIndexInWholeDictionary(wordsList, baseIndexes, indexAfterLinear);

        List<String> resultSublist = wordsList.subList(indexAfterLinear, wordsList.size());

        return new WordsResult(resultSublist, indexInWholeDictionary);
    }

    private int calculateIndexInWholeDictionary(List<String> wordsList, Map<String, Integer> baseIndexes, int indexAfterLinear) {

        String firstWordFirstLetterLowerCase = wordsList.get(0).substring(0, 1).toLowerCase();

        return baseIndexes.get(firstWordFirstLetterLowerCase) + indexAfterLinear;
    }

}