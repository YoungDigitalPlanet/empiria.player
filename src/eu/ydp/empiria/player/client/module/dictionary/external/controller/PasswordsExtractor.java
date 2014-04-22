package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Passwords;

import java.util.List;
import java.util.Map;

public class PasswordsExtractor {

    private static final String DICTIONARY_DELIMITER = "#####\n";
    public static final String WORD_DELIMITER = "\n";

    public Passwords extractPasswords(String text) {
        Map<String, List<String>> passwordsByLetter = Maps.newLinkedHashMap();
        Map<String, Integer> baseIndexes = Maps.newTreeMap();

        Iterable<String> wordsByLetters = Splitter.on(DICTIONARY_DELIMITER).split(text);

        for (String wordsByLetter : wordsByLetters) {
            if (!wordsByLetter.isEmpty()) {
                List<String> words = extractWords(wordsByLetter);
                addWords(words, passwordsByLetter);
            }
        }

        int indexSum = 0;
        for (List<String> currList : passwordsByLetter.values()) {
            String firstLetter = extractFirstWordsFirstLetter(currList);
            baseIndexes.put(firstLetter, indexSum);
            indexSum += currList.size();
        }

        return new Passwords(passwordsByLetter, baseIndexes);
    }

    private List<String> extractWords(String wordsByLetter) {
        Iterable<String> split = Splitter.on(WORD_DELIMITER).split(wordsByLetter);
        return Lists.newArrayList(split);
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
