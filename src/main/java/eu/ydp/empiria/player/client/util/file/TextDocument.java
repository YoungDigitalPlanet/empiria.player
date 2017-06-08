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

package eu.ydp.empiria.player.client.util.file;

import com.google.gwt.core.client.GWT;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileRequestException;
import eu.ydp.jsfilerequest.client.FileResponse;

public class TextDocument {

    private DocumentLoadCallback<String> callback;
    private String url;
    private String baseUrl;

    public TextDocument(String u, DocumentLoadCallback<String> c) {

        this.callback = c;
        this.url = u;
        this.baseUrl = url.substring(0, url.lastIndexOf("/") + 1);

        FileRequest request = GWT.create(FileRequest.class);

        request.setUrl(url);
        try {
            request.send("", new FileRequestCallback() {

                @Override
                public void onResponseReceived(FileRequest request, FileResponse response) {
                    callback.finishedLoading(response.getText(), baseUrl);
                }

                @Override
                public void onError(FileRequest request, Throwable exception) {
                    exception.printStackTrace();
                    callback.loadingError(exception.getMessage());
                }
            });
        } catch (FileRequestException e) {
            e.printStackTrace();
            callback.loadingError("Can't connect to the server: " + e.toString());
        }

    }

}
