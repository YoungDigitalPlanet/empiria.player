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

import com.google.gwt.dom.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.gwtutil.client.xml.XMLUtils;

/**
 * Responsible for putting meta nodes into HTML Document.
 */
public class MetaDescriptionManager {

    public void processDocument(com.google.gwt.xml.client.Document document) {
        if (document != null) {
            Element metaDescriptionNode = XMLUtils.getFirstElementWithTagName(document.getDocumentElement(), "metaDescription");
            if (metaDescriptionNode != null) {
                NodeList metaNodes = metaDescriptionNode.getElementsByTagName("meta");
                com.google.gwt.dom.client.Element htmlHeadNode = Document.get().getElementsByTagName("head").getItem(0);
                for (int n = 0; n < metaNodes.getLength(); n++) {
                    com.google.gwt.dom.client.Element currElement = convertToDomElement((Element) metaNodes.item(n));
                    htmlHeadNode.appendChild(currElement);
                }
            }
        }
    }

    private com.google.gwt.dom.client.Element convertToDomElement(Element xmlElement) {
        com.google.gwt.dom.client.Element domElement = Document.get().createElement(xmlElement.getNodeName());
        for (int a = 0; a < xmlElement.getAttributes().getLength(); a++) {
            Node currAttr = xmlElement.getAttributes().item(a);
            domElement.setAttribute(currAttr.getNodeName(), currAttr.getNodeValue());
        }
        return domElement;
    }
}
