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

package eu.ydp.empiria.player.client.module.external.interaction;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSaver;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalApiProvider;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionStateProvider {

    private final ExternalApiProvider externalApi;
    private final ExternalStateSaver stateSaver;
    private final ExternalStateEncoder stateEncoder;

    @Inject
    public ExternalInteractionStateProvider(@ModuleScoped ExternalApiProvider externalApi, @ModuleScoped ExternalStateSaver stateSaver, ExternalStateEncoder stateEncoder) {
        this.externalApi = externalApi;
        this.stateSaver = stateSaver;
        this.stateEncoder = stateEncoder;
    }

    public JSONArray getState() {
        JavaScriptObject state = externalApi.getExternalApi().getStateFromExternal();
        stateSaver.setExternalState(state);
        return stateEncoder.encodeState(state);
    }
}
