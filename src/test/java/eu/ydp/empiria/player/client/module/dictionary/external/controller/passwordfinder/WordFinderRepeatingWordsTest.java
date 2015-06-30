package eu.ydp.empiria.player.client.module.dictionary.external.controller.passwordfinder;

import com.google.common.collect.Range;

import java.util.Arrays;
import java.util.List;

public class WordFinderRepeatingWordsTest extends AbstractPasswordsFinderTestBase {

    @Override
    protected Object[][] getParams() {
        return new Object[][]{{"x", "x"}, {"xx", "xxx"}, {"xxx", "xxx"}, {"xxxx", "xxxxx"}, {"xxxxx", "xxxxx"}, {"xxxxxx", "xxxxx"},
                {"xxxxxz", "xxxxx"}, {"xxxxz", "xxxxx"},};
    }

    @Override
    protected List<String> getDictionary() {
        return Arrays.asList(new String[]{"x", "xxx", "xxxxx", "y"});
    }

    @Override
    protected Range<Integer> getDictionaryExtensionRange() {
        return getDefaultDictionaryExtensionRange();
    }

}
