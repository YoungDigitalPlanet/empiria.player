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

package eu.ydp.empiria.player.client.controller.data.library;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.gwtutil.client.PathUtil;

public class LibraryLink {

    protected String link;

    public LibraryLink(NodeList libraryNodes, String baseUrl) {

        try {
            Element linkElement = (Element) ((Element) libraryNodes.item(0)).getElementsByTagName("link").item(0);
            link = PathUtil.resolvePath(linkElement.getAttribute("href"), baseUrl);
        } catch (Exception e) {
        }
    }

    public boolean hasLink() {
        return link != null;
    }

    public String getLink() {
        return link;
    }
}
