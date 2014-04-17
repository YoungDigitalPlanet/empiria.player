package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.Set;

public interface PasswordsSocket {

	public PasswordsResult getPasswords(String letter);

	public int getPasswordIndex(String password);

	public Set<String> getLetters();
}
