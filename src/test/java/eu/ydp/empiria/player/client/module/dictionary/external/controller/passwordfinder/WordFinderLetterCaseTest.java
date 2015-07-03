package eu.ydp.empiria.player.client.module.dictionary.external.controller.passwordfinder;

import com.google.common.collect.Range;

import java.util.Arrays;
import java.util.List;

public class WordFinderLetterCaseTest extends AbstractPasswordsFinderTestBase {

    @Override
    protected Object[][] getParams() {
        return new Object[][]{{"b", "bb"}, {"ba", "bb"}, {"bax", "bb"}, {"bb", "bb"}, {"bbx", "bb"}, {"bc", "bC"}, {"bcx", "bC"},
                {"bd", "bb"}, {"be", "be"}, {"bex", "be"}, {"bg", "bg"}, {"bx", "bb"}};
    }

    @Override
    protected List<String> getDictionary() {
        return Arrays.asList(new String[]{"a", "bb", "bC", "Be", "bg"});
    }

    @Override
    protected Range<Integer> getDictionaryExtensionRange() {
        return getDefaultDictionaryExtensionRange();
    }

}
