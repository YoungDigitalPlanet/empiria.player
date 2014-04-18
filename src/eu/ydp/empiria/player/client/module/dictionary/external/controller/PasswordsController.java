package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileRequestException;
import eu.ydp.jsfilerequest.client.FileResponse;

public class PasswordsController implements PasswordsSocket, FileRequestCallback {

	private static final String PASSWORD_FILE_PATH = "dictionary/passwords/passwords.txt";
	private final String PASSWORDS_PATH;
	private static final String DICTIONARY_DELIMITER = "#####\n";

	@Inject
	private Provider<PasswordsLoadingListener> listenerProvider;
	@Inject
	private Provider<PasswordsResultFinder> finderProvider;
	@Inject
	private Provider<FileRequest> fileRequestProvider;
	@Inject
	private Logger logger;

	private Map<String, List<String>> passwords;
	private Map<String, Integer> baseIndexes;

	@Inject
	public PasswordsController(EmpiriaPaths empiriaPaths) {
		PASSWORDS_PATH = empiriaPaths.getCommonsFilePath(PASSWORD_FILE_PATH);
	}

	public void load() {
		try {
			FileRequest fileRequest = fileRequestProvider.get();
			fileRequest.setUrl(PASSWORDS_PATH);
			fileRequest.send(null, this);
		} catch (FileRequestException exception) {
			logger.error(exception);
		}
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

	@Override
	public void onResponseReceived(FileRequest request, FileResponse response) {
		passwords = new LinkedHashMap<String, List<String>>();

		String text = response.getText();

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
	public void onError(FileRequest request, Throwable exception) {
		logger.error(exception);
	}

}
