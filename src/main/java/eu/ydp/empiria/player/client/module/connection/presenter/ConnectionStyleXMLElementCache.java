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

package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.connection.ConnectionStyleNameConstants;
import eu.ydp.empiria.player.client.view.player.AbstractCache;
import eu.ydp.gwtutil.client.xml.XMLParser;

import java.util.HashMap;
import java.util.Map;

public class ConnectionStyleXMLElementCache extends AbstractCache<MultiplePairModuleConnectType, Element> {

    Map<MultiplePairModuleConnectType, String> styles = new HashMap<MultiplePairModuleConnectType, String>();
    private final XMLParser xmlParser;

    public ConnectionStyleXMLElementCache(ConnectionStyleNameConstants styleNames, XMLParser xmlParser) {
        styles.put(MultiplePairModuleConnectType.WRONG, styleNames.QP_CONNECTION_WRONG());
        styles.put(MultiplePairModuleConnectType.CORRECT, styleNames.QP_CONNECTION_CORRECT());
        styles.put(MultiplePairModuleConnectType.NONE, styleNames.QP_CONNECTION_DISABLED());
        styles.put(MultiplePairModuleConnectType.NORMAL, styleNames.QP_CONNECTION_NORMAL());
        this.xmlParser = xmlParser;
    }

    String template = "<root><connection class=\"$class\" /></root>";

    private String getTemplate(String className) {
        return template.replaceAll("\\$class", className);
    }

    private Element getXMLElement(String className) {
        return (Element) xmlParser.parse(getTemplate(className)).getDocumentElement().getFirstChild();
    }

    @Override
    protected Element getElement(MultiplePairModuleConnectType index) {
        return getXMLElement(styles.get(index));
    }

}
