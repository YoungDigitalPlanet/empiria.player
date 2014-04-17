package eu.ydp.empiria.player.client.module.dictionary.external.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringNormalizer {

	private static Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

	public static String normalize(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}
}