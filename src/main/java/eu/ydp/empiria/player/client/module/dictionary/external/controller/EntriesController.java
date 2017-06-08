/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
