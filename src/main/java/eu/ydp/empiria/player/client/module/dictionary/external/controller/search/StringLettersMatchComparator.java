package eu.ydp.empiria.player.client.module.dictionary.external.controller.search;

import java.util.Comparator;

public class StringLettersMatchComparator implements Comparator<String> {

	@Override
	public int compare(String reference, String test) {
		String referenceLower = reference.toLowerCase();
		String testLower = test.toLowerCase();
		int counter = 0;
		for (int i = 0; i < referenceLower.length() && i < testLower.length(); i++) {
			if (referenceLower.charAt(i) == testLower.charAt(i)) {
				counter++;
			} else {
				break;
			}
		}
		return reference.length() - counter;
	}

}