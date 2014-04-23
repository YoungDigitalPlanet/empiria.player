package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Words;

import java.util.List;
import java.util.Map;

public class WordsExtractor {

    private static final String DICTIONARY_DELIMITER = "#####\n";
    public static final String WORD_DELIMITER = "\n";

    public Words extractWords(String text) {
        Map<String, List<String>> wordsByLetter = splitToWordsMap(text);
        Map<String, Integer> baseIndexes = buildIndexes(wordsByLetter);

        return new Words(wordsByLetter, baseIndexes);
    }

    private Map<String, List<String>> splitToWordsMap(String text) {
        Map<String, List<String>> wordsMap = Maps.newLinkedHashMap();

        Iterable<String> parsedWordsByLetter = Splitter.on(DICTIONARY_DELIMITER).split(text);

        for (String wordsByLetter : parsedWordsByLetter) {
            if (!wordsByLetter.isEmpty()) {
                List<String> words = splitWords(wordsByLetter);
                addWords(words, wordsMap);
            }
        }
        return wordsMap;
    }

    private Map<String, Integer> buildIndexes(Map<String, List<String>> passwordsByLetter) {
        Map<String, Integer> baseIndexes = Maps.newTreeMap();
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
