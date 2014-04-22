package eu.ydp.empiria.player.client.module.dictionary.external.controller.filename;

import eu.ydp.empiria.player.client.resources.EmpiriaPaths;

import javax.inject.Inject;

public class PasswordFilenameProvider {

    private static final String PASSWORD_FILE_PATH = "dictionary/passwords/passwords.txt";

    @Inject
    private EmpiriaPaths empiriaPaths;

    public String getName(){
        return empiriaPaths.getCommonsFilePath(PASSWORD_FILE_PATH);
    }
}
