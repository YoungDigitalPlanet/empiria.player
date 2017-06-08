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

package eu.ydp.empiria.player.client.controller.session.sockets;

import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;

public interface ItemSessionSocket {

    public JSONArray getState(int itemIndex);

    public void setState(int itemIndex, JSONArray state);

    // public void updateItemVariables(int itemIndex, Map<String, Outcome> variablesMap);

    public void beginItemSession(int itemIndex);

    public void endItemSession(int itemIndex);

    public ItemOutcomeStorageImpl getOutcomeVariablesMap(int itemIndex);
}
