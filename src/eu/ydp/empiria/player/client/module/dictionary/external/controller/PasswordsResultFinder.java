package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.dictionary.external.controller.search.BinarySearchBestMatch;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.search.LinearSearch;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.search.StringIgnoreCaseComparator;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.search.StringLettersMatchComparator;

public class PasswordsResultFinder {

	private final BinarySearchBestMatch<String> bestMatch;
	private final LinearSearch<String> linearSearch;

	@Inject
	public PasswordsResultFinder(BinarySearchBestMatch<String> bestMatch, LinearSearch<String> linearSearch) {
		this.bestMatch = bestMatch;
		this.linearSearch = linearSearch;
	}

	public PasswordsResult findPhrasesMatchingPrefix(List<String> passwordsList, Map<String, Integer> baseIndexes, String prefix) {

		int index = bestMatch.search(passwordsList, prefix, new StringIgnoreCaseComparator());

		int indexAfterLinear = linearSearch.search(passwordsList, prefix, index, new StringLettersMatchComparator());

		return generatePasswordsResult(passwordsList, baseIndexes, indexAfterLinear);
	}

	private PasswordsResult generatePasswordsResult(List<String> passwordsList, Map<String, Integer> baseIndexes, int indexAfterLinear) {

		int indexInWholeDictionary = calculateIndexInWholeDictionary(passwordsList, baseIndexes, indexAfterLinear);

		List<String> resultSublist = passwordsList.subList(indexAfterLinear, passwordsList.size());

		return new PasswordsResult(resultSublist, indexInWholeDictionary);
	}

	private int calculateIndexInWholeDictionary(List<String> passwordsList, Map<String, Integer> baseIndexes, int indexAfterLinear) {

		String passwordFirstLetterLowerCase = passwordsList.get(0).substring(0, 1).toLowerCase();

		return baseIndexes.get(passwordFirstLetterLowerCase) + indexAfterLinear;
	}

}