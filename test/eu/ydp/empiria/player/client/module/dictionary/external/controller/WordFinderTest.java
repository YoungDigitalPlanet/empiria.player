package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Words;

@RunWith(MockitoJUnitRunner.class)
public class WordFinderTest {

	@InjectMocks
	private WordFinder testObj;

	@Mock
	private FirstWordFinder firstWordFinder;
	@Mock
	private WordsResultFinder finder;

	@Mock
	private Provider<WordsResultFinder> finderProvider;

	private final Map<String, List<String>> wordsByLetter = Maps.newLinkedHashMap();
	private final Map<String, Integer> baseIndexes = Maps.newTreeMap();
	private final List<String> wordsByLetterK = Lists.newArrayList("ka", "kb");
	private final List<String> wordsByLetterO = Lists.newArrayList("on", "oz");
	private Words words;

	@Before
	public void init() {
		when(finderProvider.get()).thenReturn(finder);

		wordsByLetter.put("k", wordsByLetterK);
		baseIndexes.put("k", 0);

		wordsByLetter.put("o", wordsByLetterO);
		baseIndexes.put("o", 1);

		words = new Words(wordsByLetter, baseIndexes);
	}

	@Test
	public void shoudFireFind_whenTextIsNull() {
		// given
		String text = null;
		WordsResult wordsResult = new WordsResult(wordsByLetterK, 0);
		when(firstWordFinder.find(words)).thenReturn(Optional.of(wordsResult));

		// when
		Optional<WordsResult> result = testObj.getWordsResult(text, words);

		// then
		assertThat(result.isPresent()).isTrue();
		WordsResult wordResult = result.get();
		assertThat(wordResult.getList()).isEqualTo(wordsByLetterK);
		assertThat(wordResult.getIndex()).isEqualTo(0);
	}

	@Test
	public void shoudFireFind_whenTextIsEmpty() {
		// given
		String text = "";
		WordsResult wordsResult = new WordsResult(wordsByLetterK, 0);
		when(firstWordFinder.find(words)).thenReturn(Optional.of(wordsResult));

		// when
		Optional<WordsResult> result = testObj.getWordsResult(text, words);

		// then
		assertThat(result.isPresent()).isTrue();
		WordsResult wordResult = result.get();
		assertThat(wordResult.getList()).isEqualTo(wordsByLetterK);
		assertThat(wordResult.getIndex()).isEqualTo(0);
	}

	@Test
	public void shouldReturnAbsent_whenCurrentWordsIsNull() {
		// given
		String text = "a";

		// when
		Optional<WordsResult> result = testObj.getWordsResult(text, words);

		// then
		assertThat(result).isEqualTo(Optional.<WordsResult> absent());
	}
	
	@Test 
	public void shouldReturnPresentInstance_whenTextHasOneLetter() {
		// given
		String text = "o";
		
		// when
		Optional<WordsResult> result = testObj.getWordsResult(text, words);

		// then
		assertThat(result.isPresent()).isTrue();
		WordsResult wordResult = result.get();
		assertThat(wordResult.getList()).isEqualTo(wordsByLetterO);
		assertThat(wordResult.getIndex()).isEqualTo(1);
	}

	@Test
	public void shouldFireFindPhrasesMatchingPrefix_whenTestHasMoreThanOneLetter() {
		// given
		String text = "ka";
		WordsResult wordsResult = new WordsResult(wordsByLetterK, 0);
		List<String> currentWords = words.getWordsByLetter("k");
		when(finder.findPhrasesMatchingPrefix(currentWords, baseIndexes, text)).thenReturn(wordsResult);

		// when
		Optional<WordsResult> result = testObj.getWordsResult(text, words);

		// then
		assertThat(result.isPresent()).isTrue();
		WordsResult wordResult = result.get();
		assertThat(wordResult.getList().subList(0, 1)).isEqualTo(wordsByLetterK.subList(0, 1));
		assertThat(wordResult.getIndex()).isEqualTo(0);

	}
}
