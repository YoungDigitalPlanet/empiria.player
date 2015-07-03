package eu.ydp.empiria.player.client.module.dictionary.external.controller.passwordfinder;

import com.google.common.collect.Range;

import java.util.Arrays;
import java.util.List;

public class WordFinderNextPreviousTest extends AbstractPasswordsFinderTestBase {

    @Override
    protected Object[][] getParams() {
        return new Object[][]{{"xcx", "xc"}, {"xax", "xa"},};
    }

    @Override
    protected List<String> getDictionary() {
        return Arrays.asList(new String[]{"xa", "xc", "xe"});
    }

    @Override
    protected Range<Integer> getDictionaryExtensionRange() {
        return getDefaultDictionaryExtensionRange();
    }

}
