package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.Set;

import com.google.common.base.Optional;

public interface WordsSocket {

	public Optional<WordsResult> getWords(String letter);

	public Set<String> getLetters();
}
