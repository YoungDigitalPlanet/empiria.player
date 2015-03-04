package eu.ydp.empiria.player.client.module.dictionary.external.controller.passwordfinder;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Range;

public class WordFinderNextPreviousTest extends AbstractPasswordsFinderTestBase {

	@Override
	protected Object[][] getParams() {
		return new Object[][] { { "xcx", "xc" }, { "xax", "xa" }, };
	}

	@Override
	protected List<String> getDictionary() {
		return Arrays.asList(new String[] { "xa", "xc", "xe" });
	}

	@Override
	protected Range<Integer> getDictionaryExtensionRange() {
		return getDefaultDictionaryExtensionRange();
	}

}
