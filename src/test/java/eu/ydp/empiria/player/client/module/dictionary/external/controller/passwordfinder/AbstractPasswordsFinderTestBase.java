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

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public abstract class AbstractPasswordsFinderTestBase {

    protected static final int MAX_BRACED_WORDS = 11;

    private final WordsResultFinder finder = new WordsResultFinder(new BinarySearchBestMatch<String>(), new LinearSearch<String>());
    private final WordsPermutationUtil util = new WordsPermutationUtil();

    protected abstract Object[][] getParams();

    protected abstract List<String> getDictionary();

    protected abstract Range<Integer> getDictionaryExtensionRange();

    @SuppressWarnings("unused")
    private Object[] parametersForTest() {
        Object[][] params = getParams();
        return util.parametersForTest(params, getDictionary(), getDictionaryExtensionRange());
    }

    @Test
    @Parameters
    public void test(String needle, String expectedFirstFound, List<String> words) {
        find(needle, expectedFirstFound, words);
    }

    protected Range<Integer> getDefaultDictionaryExtensionRange() {
        return Range.closed(0, MAX_BRACED_WORDS);
    }

    private void find(String needle, String expectedFirstFound, List<String> words) {
        @SuppressWarnings("unchecked")
        Map<String, Integer> baseIndexes = mock(Map.class);
        doReturn(0).when(baseIndexes).get(anyString());

        WordsResult pwds = finder.findPhrasesMatchingPrefix(words, baseIndexes, needle);
        if (expectedFirstFound == null) {
            assertThat(pwds.getList().isEmpty(), equalTo(true));
        } else {
            assertThat(pwds.getList().get(0), equalToIgnoringCase(expectedFirstFound));
        }
    }
}
