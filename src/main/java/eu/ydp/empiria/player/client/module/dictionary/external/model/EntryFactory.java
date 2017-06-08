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

package eu.ydp.empiria.player.client.module.dictionary.external.model;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.xml.IXMLParser;

public class EntryFactory {

    private static final String TAG_NAME = "word";
    private static final String TYPE_ATTR_NAME = "type";
    private static final String ENTRY_ATTR_NAME = "entry";
    private static final String ENTRY_DESCRIPTION_ATTR_NAME = "entryDescription";
    private static final String ENTRY_EXAMPLE_ATTR_NAME = "entryExample";
    private static final String ENTRY_SOUND_ATTR_NAME = "entrySound";
    private static final String ENTRY_EXAMPLE_SOUND_ATTR_NAME = "entryExampleSound";
    private static final String ENTRY_LABEL_ATTR_NAME = "label";

    @Inject
    private IXMLParser xmlParser;

    public Entry createEntryFromXMLString(String xmlString, int index) {
        return createElement(xmlString, index);
    }

    private Entry createElement(String response, int index) {
        Document document = xmlParser.parse(response);
        NodeList wordElements = document.getElementsByTagName(TAG_NAME);
        Element word = (Element) wordElements.item(index);

        String entry = fetchValue(word, ENTRY_ATTR_NAME);
        String entryDescription = fetchValue(word, ENTRY_DESCRIPTION_ATTR_NAME);
        String type = fetchValue(word, TYPE_ATTR_NAME);
        String entryExample = fetchValue(word, ENTRY_EXAMPLE_ATTR_NAME);
        String entrySound = fetchValue(word, ENTRY_SOUND_ATTR_NAME);
        String entryExampleSound = fetchValue(word, ENTRY_EXAMPLE_SOUND_ATTR_NAME);
        String label = fetchValue(word, ENTRY_LABEL_ATTR_NAME);

        return new Entry(entry, entryDescription, type, entryExample, entrySound, entryExampleSound, label);
    }

    private String fetchValue(Element element, String name) {
        return element.getAttribute(name);
    }
}
