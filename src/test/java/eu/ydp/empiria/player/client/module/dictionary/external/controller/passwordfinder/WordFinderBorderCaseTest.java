package eu.ydp.empiria.player.client.module.dictionary.external.controller.passwordfinder;

import com.google.common.collect.Range;

import java.util.Arrays;
import java.util.List;

public class WordFinderBorderCaseTest extends AbstractPasswordsFinderTestBase {

    @Override
    protected Object[][] getParams() {
        return new Object[][]{{"a", "baa"}, {"b", "baa"}, {"baa", "baa"}, {"c", "baa"},};
    }

    @Override
    protected List<String> getDictionary() {
        return Arrays.asList(new String[]{"baa"});
    }

    @Override
    protected Range<Integer> getDictionaryExtensionRange() {
        return Range.closed(0, 0);
    }

}
