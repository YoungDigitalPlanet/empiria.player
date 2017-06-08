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

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.filename.WordsFilenameProvider;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Words;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileRequestException;
import eu.ydp.jsfilerequest.client.FileResponse;

import java.util.Set;

@Singleton
public class WordsController implements WordsSocket, FileRequestCallback {

    @Inject
    private Provider<WordsLoadingListener> listenerProvider;
    @Inject
    private WordFinder wordFinder;
    @Inject
    private Provider<FileRequest> fileRequestProvider;
    @Inject
    private WordsFilenameProvider wordsFilenameProvider;
    @Inject
    private Logger logger;
    @Inject
    private WordsExtractor wordsExtractor;

    private Words words;

    public void load() {
        try {
            String wordsFilename = wordsFilenameProvider.getName();

            FileRequest fileRequest = fileRequestProvider.get();
            fileRequest.setUrl(wordsFilename);
            fileRequest.send(null, this);
        } catch (FileRequestException exception) {
            logger.error(exception);
        }
    }

    @Override
    public Optional<WordsResult> getWords(String text) {
        return wordFinder.getWordsResult(text, words);
    }

    @Override
    public Set<String> getLetters() {
        return words.getFirstLetters();
    }

    @Override
    public void onResponseReceived(FileRequest request, FileResponse response) {
        String text = response.getText();

        words = wordsExtractor.extractWords(text);

        listenerProvider.get().onWordsLoaded();
    }

    @Override
    public void onError(FileRequest request, Throwable exception) {
        logger.error(exception);
    }
}
