package eu.ydp.empiria.player.client.module.dictionary.external.controller.passwordfinder;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Range;

public class PasswordFinderSelfContainingWordsTest extends AbstractPasswordsFinderTestBase {

	@Override
	protected Object[][] getParams() {
		return new Object[][] { { "l", "leg" }, { "la", "leg" }, { "le", "leg" }, { "lz", "leg" }, { "lea", "leg" }, { "leg", "leg" }, { "lez", "leg" },
				{ "lega", "legal" }, { "legz", "leg" }, { "legaa", "legal" }, { "legal", "legal" }, { "legaz", "legal" }, { "legala", "legal" },
				{ "legali", "legalism" }, { "legalz", "legal" }, { "legalisa", "legalism" }, { "legalism", "legalism" }, { "legalisz", "legalism" },
				{ "legalisma", "legalism" }, { "xaz", "xab" }, };
	}

	@Override
	protected List<String> getDictionary() {
		return Arrays.asList(new String[] { "baa", "leg", "legal", "legalism", "xab" });
	}

	@Override
	protected Range<Integer> getDictionaryExtensionRange() {
		return getDefaultDictionaryExtensionRange();
	}
}
