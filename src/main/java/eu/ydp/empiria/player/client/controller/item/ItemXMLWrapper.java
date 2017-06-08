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

package eu.ydp.empiria.player.client.controller.item;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

import javax.annotation.PostConstruct;

public class ItemXMLWrapper {

    @Inject
    @PageScoped
    private XmlData xmlData;

    private Document document;

    @PostConstruct
    public void postConstruct() {
        document = xmlData.getDocument();
    }

    public NodeList getStyleDeclaration() {
        return getElementsByTagName("styleDeclaration");
    }

    public NodeList getAssessmentItems() {
        return getElementsByTagName("assessmentItem");
    }

    public Element getItemBody() {
        return (Element) getElementsByTagName("itemBody").item(0);
    }

    public NodeList getResponseDeclarations() {
        return getElementsByTagName("responseDeclaration");
    }

    public NodeList getExpressions() {
        return getElementsByTagName("expressions");
    }

    public String getBaseURL() {
        return xmlData.getBaseURL();
    }

    private NodeList getElementsByTagName(String nodeName) {
        return document.getElementsByTagName(nodeName);
    }

}
