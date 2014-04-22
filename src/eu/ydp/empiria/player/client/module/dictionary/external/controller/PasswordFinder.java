package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Passwords;

import java.util.List;
import java.util.Map;

public class PasswordFinder {

    @Inject
    private Provider<PasswordsResultFinder> finderProvider;

    public PasswordsResult getPasswordsResult(String text, Passwords passwords) {
        Map<String, Integer> baseIndexes = passwords.getBaseIndexes();

        if (text == null || text.isEmpty()) {
            return new PasswordsResult(passwords.getPasswordsByLetter("a"), baseIndexes.get("a"));
        }
        String lowerCaseText = text.toLowerCase();

        String firstLetter = getFirstLetter(lowerCaseText);
        List<String> currentPasswords = passwords.getPasswordsByLetter(firstLetter);

        if (hasOnlyOneLetter(lowerCaseText)) {
            Integer baseIndex = baseIndexes.get(lowerCaseText);
            if (currentPasswords != null && baseIndex != null) {
                return new PasswordsResult(currentPasswords, baseIndex);
            } else {
                return null;
            }
        }

        if (currentPasswords == null) {
            return null;
        }
        PasswordsResultFinder finder = finderProvider.get();

        return finder.findPhrasesMatchingPrefix(currentPasswords, baseIndexes, lowerCaseText);
    }

    private boolean hasOnlyOneLetter(String lowerCaseText) {
        return lowerCaseText.length() == 1;
    }

    private String getFirstLetter(String lowerCaseText) {
        return lowerCaseText.substring(0, 1);
    }
}
