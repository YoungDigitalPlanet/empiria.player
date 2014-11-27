package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import static org.fest.assertions.api.Assertions.*;

import java.util.*;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Words;

public class FirstWordFinderTest {

	private final Map<String, List<String>> wordsByLetter = Maps.newLinkedHashMap();
	private final Map<String, Integer> baseIndexes = Maps.newTreeMap();
	private final FirstWordFinder testObj = new FirstWordFinder();

	@Test
	public void shouldReturnAbsent_whenWordsAreEmpty() {
		// given
		Words words = new Words(wordsByLetter, baseIndexes);

		// when
		Optional<WordsResult> result = testObj.find(words);
		
		// then
		assertThat(result).isEqualTo(Optional.<WordsResult> absent());
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
