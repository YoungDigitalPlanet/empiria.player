package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.*;

import com.google.common.base.*;
import com.google.inject.*;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Words;

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
			return Optional.of(new WordsResult(currentWords, baseIndexes.get(lowerCaseText)));
		}

		WordsResultFinder finder = finderProvider.get();
		return Optional.of(finder.findPhrasesMatchingPrefix(currentWords, baseIndexes, lowerCaseText));
	}

	private boolean hasOnlyOneLetter(String lowerCaseText) {
		return lowerCaseText.length() == 1;
	}

	private String getFirstLetter(String lowerCaseText) {
		return lowerCaseText.substring(0, 1);
	}
}
