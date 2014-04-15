package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.dictionary.external.util.DocumentLoader;
import eu.ydp.empiria.player.client.module.dictionary.external.util.DocumentLoadingListener;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;

public class PasswordsController implements DocumentLoadingListener, PasswordsSocket {

	private static final String PASSWORD_FILE_PATH = "dictionary/passwords/passwords.txt";
	private final String PASSWORDS_PATH;
	private static final String DICTIONARY_DELIMITER = "#####\n";

	@Inject
	private Provider<PasswordsLoadingListener> listenerProvider;
	@Inject
	private Provider<PasswordsResultFinder> finderProvider;

	private Map<String, List<String>> passwords;
	private Map<String, Integer> baseIndexes;

	@Inject
	public PasswordsController(EmpiriaPaths empiriaPaths) {
		PASSWORDS_PATH = empiriaPaths.getCommonsFilePath(PASSWORD_FILE_PATH);
	}

	public void load() {
		DocumentLoader.load(PASSWORDS_PATH, this);
	}

	@Override
	public void onDocumentLoaded(String text) {

		passwords = new LinkedHashMap<String, List<String>>();

		String[] wordsByLetters = text.split(DICTIONARY_DELIMITER);

		for (int i = 0; i < wordsByLetters.length; i++) {
			if (wordsByLetters[i].length() > 0) {
				List<String> letters = Arrays.asList(wordsByLetters[i].split("\n"));
				passwords.put(letters.get(0).substring(0, 1).toLowerCase(), letters);
			}
		}

		baseIndexes = new TreeMap<String, Integer>();
		int indexSum = 0;

		for (List<String> currList : passwords.values()) {
			baseIndexes.put(currList.get(0).substring(0, 1).toLowerCase(), indexSum);
			indexSum += currList.size();
		}

		listenerProvider.get().onPasswordsLoaded();
	}

	@Override
	public void onDocumentLoadError(String error) {
		Window.alert("Error loading passwords list:\n" + error);
	}

	@Override
	public PasswordsResult getPasswords(String text) {
		if (passwords == null) {
			return new PasswordsResult();
		}
		if (text == null || text.length() == 0) {
			return new PasswordsResult(passwords.get("a"), baseIndexes.get("a"));
		}
		if (text.length() == 1) {
			List<String> pwds = passwords.get(text.toLowerCase());
			Integer baseIndex = baseIndexes.get(text.toLowerCase());
			if (pwds != null && baseIndex != null) {
				return new PasswordsResult(pwds, baseIndex);
			} else {
				return null;
			}
		}

		String letter = text.substring(0, 1).toLowerCase();
		List<String> currPasswords = passwords.get(letter);
		if (currPasswords == null) {
			return null;
		}
		PasswordsResultFinder finder = finderProvider.get();
		PasswordsResult matchingPasswords = finder.findPhrasesMatchingPrefix(currPasswords, baseIndexes, text.toLowerCase());
		return matchingPasswords;
	}

	@Override
	public int getPasswordIndex(String password) {
		if (password == null || password.length() == 0) {
			return -1;
		}
		String letter = password.substring(0, 1).toLowerCase();
		Iterator<String> letters = passwords.keySet().iterator();
		int index = 0;
		while (true) {
			String currLetter = letters.next();
			if (currLetter.equals(letter)) {
				break;
			}
			index += passwords.get(currLetter).size();
		}
		index += passwords.get(letter).indexOf(password);
		return index;
	}

	@Override
	public Set<String> getLetters() {
		return passwords.keySet();
	}

}
