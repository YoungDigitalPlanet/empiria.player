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

package eu.ydp.empiria.player.client.controller.report.table.modification;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class ItemIndexAppender {

    private static final String ITEM_INDEX = "itemIndex";
    private static final String INFO = "info";
    private static final String URL = "url";
    private static final String LINK = "link";

    public void appendToLinkTags(int itemIndex, Element cellElement) {
        NodeList linkNodes = cellElement.getElementsByTagName(LINK);
        for (int i = 0; i < linkNodes.getLength(); i++) {
            Element linkNode = (Element) linkNodes.item(i);
            boolean hasURL = linkNode.hasAttribute(URL);
            if (!hasURL) {
                addItemIndexAttrIfNotExists(itemIndex, linkNode);
            }
        }
    }

    public void appendToInfoTags(int itemIndex, Element cellElement) {
        NodeList infoNodes = cellElement.getElementsByTagName(INFO);
        for (int i = 0; i < infoNodes.getLength(); i++) {
            Element infoNode = (Element) infoNodes.item(i);
            addItemIndexAttrIfNotExists(itemIndex, infoNode);
        }
    }

    private void addItemIndexAttrIfNotExists(int itemIndex, Element element) {
        if (!element.hasAttribute(ITEM_INDEX)) {
            element.setAttribute(ITEM_INDEX, String.valueOf(itemIndex));
        }
    }

}
