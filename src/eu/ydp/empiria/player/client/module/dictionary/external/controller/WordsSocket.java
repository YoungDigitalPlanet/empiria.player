package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.Set;

public interface WordsSocket {

	public WordsResult getWords(String letter);

	public Set<String> getLetters();
}
