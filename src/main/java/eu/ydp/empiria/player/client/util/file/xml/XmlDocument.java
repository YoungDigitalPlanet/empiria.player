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

package eu.ydp.empiria.player.client.util.file.xml;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;
import eu.ydp.empiria.player.client.util.file.DocumentLoadCallback;
import eu.ydp.empiria.player.client.util.file.TextDocument;

public class XmlDocument {

    private DocumentLoadCallback<Document> callback;
    private String url;

    public XmlDocument(String url, DocumentLoadCallback<Document> callback) {

        this.callback = callback;
        this.url = url;

        new TextDocument(this.url, new DocumentLoadCallback<String>() {

            @Override
            public void loadingError(String message) {
                XmlDocument.this.callback.loadingError(message);
            }

            @Override
            public void finishedLoading(String text, String baseUrl) {
                try {
                    Document dom = XMLParser.parse(text);
                    XmlDocument.this.callback.finishedLoading(dom, baseUrl);
                } catch (DOMParseException e) {
                    e.printStackTrace();
                    XmlDocument.this.callback.loadingError("Could not parse file: " + XmlDocument.this.url);
                }
            }
        });

    }
}
