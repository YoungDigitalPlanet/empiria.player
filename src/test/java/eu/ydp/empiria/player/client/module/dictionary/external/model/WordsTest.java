package eu.ydp.empiria.player.client.module.dictionary.external.model;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WordsTest {

    private Words testObj;
    private final LinkedHashMap<String, List<String>> wordsByLetter = Maps.newLinkedHashMap();
    private final TreeMap<String, Integer> baseIndexes = Maps.newTreeMap();

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
