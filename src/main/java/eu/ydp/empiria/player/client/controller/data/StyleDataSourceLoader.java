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

package eu.ydp.empiria.player.client.controller.data;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.style.StyleDocument;
import eu.ydp.empiria.player.client.util.file.DocumentLoadCallback;
import eu.ydp.empiria.player.client.util.file.TextDocument;
import eu.ydp.gwtutil.client.PathUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StyleDataSourceLoader {

    private Map<String, StyleDocument> documents;
    private Map<String, String> errorMessages;
    private Map<String, List<DocumentLoadCallback<StyleDocument>>> pending;

    @Inject
    public StyleDataSourceLoader() {
        documents = new HashMap<>();
        errorMessages = new HashMap<>();
        pending = new HashMap<>();
    }

    public synchronized void load(String url, final DocumentLoadCallback<StyleDocument> callback) {
        final String urlNormalized = PathUtil.normalizePath(url);
        if (errorMessages.containsKey(urlNormalized)) {
            callback.loadingError(errorMessages.get(urlNormalized));
        } else if (documents.containsKey(urlNormalized)) {
            callback.finishedLoading(documents.get(urlNormalized), documents.get(urlNormalized).getBasePath());
        } else if (pending.containsKey(urlNormalized)) {
            pending.get(urlNormalized).add(callback);
        } else {
            pending.put(urlNormalized, new ArrayList<DocumentLoadCallback<StyleDocument>>());
            pending.get(urlNormalized).add(callback);
            new TextDocument(urlNormalized, new DocumentLoadCallback<String>() {

                @Override
                public void finishedLoading(String value, String baseUrl) {
                    StyleDocument sd = new StyleDocument(CssParser.parseCss(value), baseUrl);
                    documents.put(urlNormalized, sd);
                    for (DocumentLoadCallback<StyleDocument> currCallback : pending.get(urlNormalized)) {
                        currCallback.finishedLoading(sd, baseUrl);
                    }
                    pending.remove(urlNormalized);
                }

                @Override
                public void loadingError(String message) {
                    errorMessages.put(urlNormalized, message);
                    for (DocumentLoadCallback<StyleDocument> currCallback : pending.get(urlNormalized)) {
                        currCallback.loadingError(message);
                    }
                    pending.remove(urlNormalized);
                }
            });
        }
    }
}
