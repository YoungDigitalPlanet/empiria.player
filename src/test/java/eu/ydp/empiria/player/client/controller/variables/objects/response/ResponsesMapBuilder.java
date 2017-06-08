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

package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResponsesMapBuilder {

    public ItemResponseManager buildResponseManager(Map<String, Response> responses) {
        ItemResponseManager responseManager = mock(ItemResponseManager.class);
        for (Map.Entry<String, Response> entry : responses.entrySet()) {
            when(responseManager.getVariable(entry.getKey())).thenReturn(entry.getValue());
            when(responseManager.containsResponse(entry.getKey())).thenReturn(true);
        }
        when(responseManager.getResponses()).thenReturn(responses.values());
        when(responseManager.getVariableIdentifiers()).thenReturn(responses.keySet());

        return responseManager;
    }

    public ItemResponseManager buildResponseManager(Response... responses) {
        ItemResponseManager responseManager = mock(ItemResponseManager.class);
        Set<String> identifiers = Sets.newHashSet();

        for (Response response : responses) {
            when(responseManager.getVariable(response.getID())).thenReturn(response);
            when(responseManager.containsResponse(response.getID())).thenReturn(true);
            identifiers.add(response.getID());
        }
        when(responseManager.getResponses()).thenReturn(Lists.newArrayList(responses));
        when(responseManager.getVariableIdentifiers()).thenReturn(identifiers);

        return responseManager;
    }

    public Map<String, Response> buildResponsesMap(Response... responses) {
        Map<String, Response> responsesMap = Maps.newHashMap();

        for (Response response : responses) {
            responsesMap.put(response.identifier, response);
        }
        return responsesMap;
    }

}
