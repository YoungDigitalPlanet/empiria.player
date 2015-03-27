package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.List;

import com.google.common.base.Optional;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Words;

public class FirstWordFinder {
	
	public Optional<WordsResult> find(Words words) {
		Optional<String> firstIndex = words.getFirstIndex();
		Optional<WordsResult> foundWords = Optional.absent();

		if (firstIndex.isPresent()) {
			foundWords = Optional.of(getWords(words, firstIndex.get()));
		}

		return foundWords;
	}

	private WordsResult getWords(Words words, String firstIndex) {
		List<String> wordsByLetter = words.getWordsByLetter(firstIndex);
		int baseIndex = words.getBaseIndexes().get(firstIndex);

		return new WordsResult(wordsByLetter, baseIndex);
	}
}
