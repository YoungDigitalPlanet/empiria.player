package eu.ydp.empiria.player.client.module.dictionary.external.controller.passwordfinder;

import com.google.common.collect.Range;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.WordsResult;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.WordsResultFinder;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.search.BinarySearchBestMatch;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.search.LinearSearch;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class PasswordsFinderTest {

    protected static final int MAX_BRACED_WORDS = 11;

    private final WordsResultFinder testObj = new WordsResultFinder(new BinarySearchBestMatch<String>(), new LinearSearch<String>());
    private static final WordsPermutationUtil util = new WordsPermutationUtil();
    private static final Range<Integer> DEFAULT_RANGE = Range.closed(0, MAX_BRACED_WORDS);

    @Test
    @Parameters(method = "borderCase, letterCase, finderNext, repeatingWords, selfContainingWords, standard")
    public void shouldFindWords(String searchedString, String expectedFirstFound, List<String> words) {
        Map<String, Integer> baseIndexes = mock(Map.class);
        when(baseIndexes.get(anyString())).thenReturn(0);

        WordsResult result = testObj.findPhrasesMatchingPrefix(words, baseIndexes, searchedString);

        assertThat(result.getList().get(0), equalToIgnoringCase(expectedFirstFound));
    }

    Object[] borderCase() {
        String[][] params = new String[][]{{"a", "baa"}, {"b", "baa"}, {"baa", "baa"}, {"c", "baa"},};
        List<String> dictionary = Arrays.asList("baa");
        Range<Integer> dictionaryExtensionRange = Range.closed(0, 0);
        return util.parametersForTest(params, dictionary, dictionaryExtensionRange);
    }

    Object[] letterCase() {
        String[][] params = new String[][]{{"b", "bb"}, {"ba", "bb"}, {"bax", "bb"}, {"bb", "bb"}, {"bbx", "bb"}, {"bc", "bC"}, {"bcx", "bC"},
                {"bd", "bb"}, {"be", "be"}, {"bex", "be"}, {"bg", "bg"}, {"bx", "bb"}};
        List<String> dictionary = Arrays.asList("a", "bb", "bC", "Be", "bg");
        Range<Integer> dictionaryExtensionRange = DEFAULT_RANGE;
        return util.parametersForTest(params, dictionary, dictionaryExtensionRange);
    }

    Object[] finderNext() {
        String[][] params = new String[][]{{"xcx", "xc"}, {"xax", "xa"},};
        List<String> dictionary = Arrays.asList("xa", "xc", "xe");
        Range<Integer> dictionaryExtensionRange = DEFAULT_RANGE;
        return util.parametersForTest(params, dictionary, dictionaryExtensionRange);
    }

    Object[] repeatingWords() {
        String[][] params = new String[][]{{"x", "x"}, {"xx", "xxx"}, {"xxx", "xxx"}, {"xxxx", "xxxxx"}, {"xxxxx", "xxxxx"}, {"xxxxxx", "xxxxx"},
                {"xxxxxz", "xxxxx"}, {"xxxxz", "xxxxx"},};
        List<String> dictionary = Arrays.asList("x", "xxx", "xxxxx", "y");
        Range<Integer> dictionaryExtensionRange = DEFAULT_RANGE;

        return util.parametersForTest(params, dictionary, dictionaryExtensionRange);
    }

    Object[] selfContainingWords() {
        String[][] params = new String[][]{{"l", "leg"}, {"la", "leg"}, {"le", "leg"}, {"lz", "leg"}, {"lea", "leg"}, {"leg", "leg"}, {"lez", "leg"},
                {"lega", "legal"}, {"legz", "leg"}, {"legaa", "legal"}, {"legal", "legal"}, {"legaz", "legal"}, {"legala", "legal"},
                {"legali", "legalism"}, {"legalz", "legal"}, {"legalisa", "legalism"}, {"legalism", "legalism"}, {"legalisz", "legalism"},
                {"legalisma", "legalism"}, {"xaz", "xab"},};
        List<String> dictionary = Arrays.asList("baa", "leg", "legal", "legalism", "xab");
        Range<Integer> dictionaryExtensionRange = DEFAULT_RANGE;
        return util.parametersForTest(params, dictionary, dictionaryExtensionRange);
    }

    Object[] standard() {
        String[][] params = new String[][]{{"b", "baa"}, {"bb", "baa"}, {"bbb", "baa"}, {"bbbb", "baa"}, {"bbbz", "baa"}, {"bbb", "baa"}, {"bca", "bcc"},
                {"bcb", "bcc"}, {"bcba", "bcc"}, {"bcbz", "bcc"}, {"bcz", "bcc"}, {"bca", "bcc"}, {"xaa", "xab"}, {"xab", "xab"},
                {"xaba", "xab"}, {"xaz", "xab"},};
        List<String> dictionary = Arrays.asList("baa", "bcc", "xab");
        Range<Integer> dictionaryExtensionRange = DEFAULT_RANGE;
        return util.parametersForTest(params, dictionary, dictionaryExtensionRange);
    }

}
