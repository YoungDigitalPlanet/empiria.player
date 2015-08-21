package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.filename.DictionaryFilenameProvider;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.scheduler.Scheduler;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileRequestException;

@Singleton
public class EntriesController {

    @Inject
    private Scheduler scheduler;
    @Inject
    private DictionaryFilenameProvider dictionaryFilenameProvider;
    @Inject
    private DictionaryModuleFactory dictionaryModuleFactory;
    @Inject
    private Provider<FileRequest> fileRequestProvider;
    @Inject
    private Logger logger;

    public void loadEntry(String password, final int index, final boolean playSound) {
        final String path = dictionaryFilenameProvider.getFilePathForIndex(index);

        scheduler.scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
                try {
                    FileRequestCallback fileRequestCallback = dictionaryModuleFactory.createFileRequestCallback(index, playSound);

                    FileRequest fileRequest = fileRequestProvider.get();
                    fileRequest.setUrl(path);
                    fileRequest.send(null, fileRequestCallback);
                } catch (FileRequestException exception) {
                    logger.error(exception);
                }
            }
        });
    }
}
