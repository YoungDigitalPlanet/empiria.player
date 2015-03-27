package eu.ydp.empiria.player.client.module.dictionary.external.controller.filename;

import eu.ydp.empiria.player.client.resources.EmpiriaPaths;

import javax.inject.Inject;

public class WordsFilenameProvider {

    private static final String WORDS_FILE_PATH = "dictionary/words/words.txt";

    @Inject
    private EmpiriaPaths empiriaPaths;

    public String getName() {
        return empiriaPaths.getCommonsFilePath(WORDS_FILE_PATH);
    }
}
