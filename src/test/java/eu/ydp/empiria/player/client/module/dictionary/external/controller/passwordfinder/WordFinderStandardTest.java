package eu.ydp.empiria.player.client.module.dictionary.external.controller.passwordfinder;

import com.google.common.collect.Range;

import java.util.Arrays;
import java.util.List;

public class WordFinderStandardTest extends AbstractPasswordsFinderTestBase {

    @Override
    protected Object[][] getParams() {
        return new Object[][]{{"b", "baa"}, {"bb", "baa"}, {"bbb", "baa"}, {"bbbb", "baa"}, {"bbbz", "baa"}, {"bbb", "baa"}, {"bca", "bcc"},
                {"bcb", "bcc"}, {"bcba", "bcc"}, {"bcbz", "bcc"}, {"bcz", "bcc"}, {"bca", "bcc"}, {"xaa", "xab"}, {"xab", "xab"},
                {"xaba", "xab"}, {"xaz", "xab"},};
    }

    @Override
    protected List<String> getDictionary() {
        return Arrays.asList(new String[]{"baa", "bcc", "xab"});
    }

    @Override
    protected Range<Integer> getDictionaryExtensionRange() {
        return getDefaultDictionaryExtensionRange();
    }

}
