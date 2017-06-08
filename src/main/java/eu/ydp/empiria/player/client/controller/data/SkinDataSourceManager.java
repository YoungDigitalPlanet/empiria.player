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

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.controller.data.events.SkinDataLoaderListener;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.util.file.DocumentLoadCallback;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.util.file.xml.XmlDocument;

import java.util.ArrayList;
import java.util.List;

public class SkinDataSourceManager {

    private final SkinDataLoaderListener loadListener;

    private StyleLinkDeclaration styleDeclaration;

    private final MetaDescriptionManager metaDescriptionManager;

    private XmlData skinData;

    public SkinDataSourceManager(SkinDataLoaderListener listener) {
        loadListener = listener;
        metaDescriptionManager = new MetaDescriptionManager();
    }

    public void load(String url) {
        new XmlDocument(url, new DocumentLoadCallback<Document>() {

            @Override
            public void loadingError(String error) {
                loadListener.onSkinLoadError();
            }

            @Override
            public void finishedLoading(Document document, String baseURL) {
                prepareSkin(new XmlData(document, baseURL));
            }
        });
    }

    public Node getBodyNode() {
        return skinData.getDocument().getElementsByTagName("itemBody").item(0);
    }

    public XmlData getSkinData() {
        return skinData;
    }

    public List<String> getStyleLinksForUserAgent(String userAgent) {
        if (styleDeclaration != null) {
            return styleDeclaration.getStyleLinksForUserAgent(userAgent);
        }
        return new ArrayList<String>();
    }

    private void prepareSkin(XmlData data) {
        Document document = data.getDocument();

        skinData = data;
        styleDeclaration = new StyleLinkDeclaration(document.getElementsByTagName("styleDeclaration"), data.getBaseURL());
        metaDescriptionManager.processDocument(document);

        loadListener.onSkinLoad();
    }
}
