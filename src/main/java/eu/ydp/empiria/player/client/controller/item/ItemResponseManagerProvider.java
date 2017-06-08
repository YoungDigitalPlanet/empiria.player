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

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseNodeParser;

public class ItemResponseManagerProvider implements Provider<ItemResponseManager> {

    private final Provider<ItemXMLWrapper> xmlMapperProvider;
    private final ResponseNodeParser nodeParser;

    public ItemResponseManagerProvider(Provider<ItemXMLWrapper> xmlMapperProvider, ResponseNodeParser nodeParser) {
        this.xmlMapperProvider = xmlMapperProvider;
        this.nodeParser = nodeParser;
    }

    private ItemResponseManager getResponseManager() {
        return new ItemResponseManager(xmlMapperProvider.get().getResponseDeclarations(), nodeParser);
    }

    @Override
    public ItemResponseManager get() {
        return getResponseManager();
    }
}
