package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Words;

import java.util.List;
import java.util.Map;

public class WordFinder {

    @Inject
    private Provider<WordsResultFinder> finderProvider;

    public WordsResult getWordsResult(String text, Words words) {
        Map<String, Integer> baseIndexes = words.getBaseIndexes();

        if (text == null || text.isEmpty()) {
            return new WordsResult(words.getWordsByLetter("a"), baseIndexes.get("a"));
        }
        String lowerCaseText = text.toLowerCase();

        String firstLetter = getFirstLetter(lowerCaseText);
        List<String> currentWords = words.getWordsByLetter(firstLetter);

        if (hasOnlyOneLetter(lowerCaseText)) {
            Integer baseIndex = baseIndexes.get(lowerCaseText);
            if (currentWords != null && baseIndex != null) {
                return new WordsResult(currentWords, baseIndex);
            } else {
                return null;
            }
        }

        if (currentWords == null) {
            return null;
        }
        WordsResultFinder finder = finderProvider.get();

        return finder.findPhrasesMatchingPrefix(currentWords, baseIndexes, lowerCaseText);
    }

    private boolean hasOnlyOneLetter(String lowerCaseText) {
        return lowerCaseText.length() == 1;
    }

    private String getFirstLetter(String lowerCaseText) {
        return lowerCaseText.substring(0, 1);
    }
}
