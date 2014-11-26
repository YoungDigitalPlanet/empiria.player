package eu.ydp.empiria.player.client.module.dictionary.external.model;

import java.util.*;

import com.google.common.base.Optional;

public class Words {
	private final Map<String, List<String>> wordsByLetter;
	private final Map<String, Integer> baseIndexes;

	public Words(Map<String, List<String>> wordsByLetter, Map<String, Integer> baseIndexes) {
		this.wordsByLetter = wordsByLetter;
		this.baseIndexes = baseIndexes;
	}

	public Map<String, Integer> getBaseIndexes() {
		return baseIndexes;
	}

	public Set<String> getFirstLetters() {
		return wordsByLetter.keySet();
	}

	public Optional<String> getFirstIndex() {
		Optional<String> optional = Optional.absent();
		if (!baseIndexes.isEmpty()) {
			optional = Optional.of(baseIndexes.keySet().iterator().next());
		}
		return optional;
	}

	public List<String> getWordsByLetter(String firstLetter) {
		return wordsByLetter.get(firstLetter);
	}
}
