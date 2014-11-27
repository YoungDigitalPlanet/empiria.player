package eu.ydp.empiria.player.client.module.dictionary.external.model;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

public class WordsTest {

	private Words testObj;
	private Map<String, List<String>> wordsByLetter;
	private Map<String, Integer> baseIndexes;

	@Before
	public void init() {
		wordsByLetter = Maps.newLinkedHashMap();
		baseIndexes = Maps.newTreeMap();
	}

	@Test
	public void shouldReturnNull_whenBaseIndexesIsEmpty() {
		// given
		testObj = new Words(wordsByLetter, baseIndexes);

		// when
		Optional<String> result = testObj.getFirstIndex();

		// than
		assertFalse(result.isPresent());
	}

	@Test
	public void shouldReturnFirstIndex_whenBaseIndexesIsNotEmpty() {
		// given
		List<String> wordsByLetterK = Lists.newArrayList("ka", "kb");
		wordsByLetter.put("k", wordsByLetterK);
		baseIndexes.put("k", 0);
		List<String> wordsByLetterO = Lists.newArrayList("on", "oz");
		wordsByLetter.put("o", wordsByLetterO);
		baseIndexes.put("o", 1);
		
		testObj = new Words(wordsByLetter, baseIndexes);

		// when
		Optional<String> result = testObj.getFirstIndex();

		// than
		assertEquals(result.get(), "k");
	}

}
