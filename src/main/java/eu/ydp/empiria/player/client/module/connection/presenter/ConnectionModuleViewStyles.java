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

import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.connection.ConnectionStyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.xml.XMLParser;

import javax.inject.Inject;
import java.util.Map;

public class ConnectionModuleViewStyles {
    private final StyleSocket styleSocket;
    private final ConnectionStyleXMLElementCache cache;

    @Inject
    public ConnectionModuleViewStyles(StyleSocket styleSocket, ConnectionStyleNameConstants styleNames, XMLParser xmlParser) {
        this.styleSocket = styleSocket;
        cache = new ConnectionStyleXMLElementCache(styleNames, xmlParser);
    }

    public Map<String, String> getStyles(MultiplePairModuleConnectType type) {
        return styleSocket.getOrgStyles(cache.getOrCreateAndPut(type));
    }
}
