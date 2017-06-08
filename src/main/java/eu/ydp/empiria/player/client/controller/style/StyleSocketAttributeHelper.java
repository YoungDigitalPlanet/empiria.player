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

package eu.ydp.empiria.player.client.controller.style;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.util.BooleanUtils;
import eu.ydp.gwtutil.client.xml.XMLParser;

import java.util.Map;

@Singleton
public class StyleSocketAttributeHelper {

    private final XMLParser xmlParser;
    private final BooleanUtils booleanUtil;
    private final StyleSocket styleSocket;

    @Inject
    public StyleSocketAttributeHelper(XMLParser xmlParser, BooleanUtils booleanUtil, StyleSocket styleSocket) {
        this.xmlParser = xmlParser;
        this.booleanUtil = booleanUtil;
        this.styleSocket = styleSocket;
    }

    private Map<String, String> getStyleValue(String attribute) {
        String xml = "<root><" + attribute + " class=\"" + attribute + "\"/></root>";
        Document document = xmlParser.parse(xml);
        Element documentElement = document.getDocumentElement();
        Element firstChild = (Element) documentElement.getFirstChild();
        return styleSocket.getStyles(firstChild);
    }

    private String getString(String nodeName, String attribute) {
        Map<String, String> styles = getStyleValue(nodeName);
        return styles.get(attribute);
    }

    public boolean getBoolean(String nodeName, String attribute) {
        String value = getString(nodeName, attribute);
        return booleanUtil.getBoolean(value);
    }
}
