package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;

public interface ExplanationListener {

    public void onEntryLoaded(Entry entry);

    public void onBackClick();
}
