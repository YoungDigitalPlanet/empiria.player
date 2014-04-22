package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.Set;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.dictionary.external.controller.filename.PasswordFilenameProvider;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Passwords;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileRequestException;
import eu.ydp.jsfilerequest.client.FileResponse;

public class PasswordsController implements PasswordsSocket, FileRequestCallback {

    @Inject
    private Provider<PasswordsLoadingListener> listenerProvider;
    @Inject
    private PasswordFinder passwordFinder;
    @Inject
    private Provider<FileRequest> fileRequestProvider;
    @Inject
    private PasswordFilenameProvider passwordFilenameProvider;
    @Inject
    private Logger logger;
    @Inject
    private PasswordsExtractor passwordsExtractor;

    private Passwords passwords;

    public void load() {
        try {
            String passwordFilename = passwordFilenameProvider.getName();

            FileRequest fileRequest = fileRequestProvider.get();
            fileRequest.setUrl(passwordFilename);
            fileRequest.send(null, this);
        } catch (FileRequestException exception) {
            logger.error(exception);
        }
    }

    @Override
    public PasswordsResult getPasswords(String text) {
        return passwordFinder.getPasswordsResult(text, passwords);
    }

    @Override
    public Set<String> getLetters() {
        return passwords.getFirstLetters();
    }

    @Override
    public void onResponseReceived(FileRequest request, FileResponse response) {
        String text = response.getText();

        passwords = passwordsExtractor.extractPasswords(text);

        listenerProvider.get().onPasswordsLoaded();
    }

    @Override
    public void onError(FileRequest request, Throwable exception) {
        logger.error(exception);
    }
}
