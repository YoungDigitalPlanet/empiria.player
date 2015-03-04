package eu.ydp.empiria.player.client.module.dictionary.external.controller.passwordfinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

public class WordsPermutationUtil {

	private final String HEAD_ELEMENT = "a";
	private final String TAIL_ELEMENT = "z";

	public Object[] parametersForTest(Object[][] params, List<String> words, Range<Integer> range) {
		List<List<String>> wordsPermutations = createWordLists(words, range);
		List<Object[]> paramsWithWords = createParamsWithWords(params, wordsPermutations);
		return paramsWithWords.toArray();
	}

	private List<Object[]> createParamsWithWords(Object[][] params, List<List<String>> words) {
		List<Object[]> allParams = new ArrayList<Object[]>();
		for (Object[] paramsPermutation : params) {
			for (List<String> currWords : words) {
				Object[] currParams = createParamsWithWordsPermutation(paramsPermutation, currWords);
				allParams.add(currParams);
			}
		}
		return allParams;
	}

	private Object[] createParamsWithWordsPermutation(Object[] params, List<String> words) {
		List<Object> paramsList = Lists.newArrayList(Arrays.asList(params));
		paramsList.add(words);
		return paramsList.toArray();
	}

	private List<List<String>> createWordLists(List<String> words, Range<Integer> range) {
		List<List<String>> wordsPermutated = Lists.newArrayList();
		for (int i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
			if (i > 0) {
				LinkedList<String> currPermutationHead = createWordsPermutation(words, i, Ending.HEAD);
				wordsPermutated.add(currPermutationHead);
				LinkedList<String> currPermutationTail = createWordsPermutation(words, i, Ending.TAIL);
				wordsPermutated.add(currPermutationTail);
			}
			LinkedList<String> currPermutationBoth = createWordsPermutation(words, i, Ending.BOTH);
			wordsPermutated.add(currPermutationBoth);
		}
		return wordsPermutated;
	}

	private LinkedList<String> createWordsPermutation(List<String> words, int i, Ending ending) {
		LinkedList<String> currPermutation = Lists.newLinkedList(words);
		for (int k = 0; k < i; k++) {
			if (ending == Ending.HEAD || ending == Ending.BOTH) {
				String currHeadPwd = Strings.padEnd(HEAD_ELEMENT, i - k, HEAD_ELEMENT.charAt(0));
				currPermutation.push(currHeadPwd);
			}
			if (ending == Ending.TAIL || ending == Ending.BOTH) {
				String currTailPwd = Strings.padEnd(TAIL_ELEMENT, k, TAIL_ELEMENT.charAt(0));
				currPermutation.add(currTailPwd);
			}
		}
		return currPermutation;
	}

	private enum Ending {
		HEAD, TAIL, BOTH
	};

}
