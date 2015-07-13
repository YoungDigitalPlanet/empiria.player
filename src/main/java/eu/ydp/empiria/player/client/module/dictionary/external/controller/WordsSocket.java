package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.common.base.Optional;

import java.util.Set;

public interface WordsSocket {

    public Optional<WordsResult> getWords(String letter);

    public Set<String> getLetters();
}
