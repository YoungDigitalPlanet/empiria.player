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

package eu.ydp.empiria.player.client.module.external.presentation;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.common.ExternalFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApiNullObject;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSaver;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSetter;
import eu.ydp.empiria.player.client.module.external.common.view.ExternalView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import javax.annotation.PostConstruct;

public class ExternalPresentationPresenter implements ExternalFrameLoadHandler<ExternalApi> {

    private final ExternalStateEncoder stateEncoder;
    private final ExternalView<ExternalApi, ExternalEmpiriaApi> view;
    private final ExternalStateSaver stateSaver;
    private final ExternalPaths externalPaths;
    private final ExternalEmpiriaApi empiriaApi;
    private final EventsBus eventsBus;
    private final ExternalStateSetter externalStateSetter;


    private PlayerEventHandler updateFrameUrlForIE11Hack = new PlayerEventHandler() {
        @Override
        public void onPlayerEvent(PlayerEvent event) {
            view.setIframeUrl(externalPaths.getExternalEntryPointPath());
        }
    };

    private ExternalApi externalApi;

    @Inject
    public ExternalPresentationPresenter(ExternalStateEncoder stateEncoder, final ExternalView<ExternalApi, ExternalEmpiriaApi> view,
                                         @ModuleScoped ExternalStateSaver stateSaver,
                                         @ModuleScoped final ExternalPaths externalPaths,
                                         ExternalEmpiriaApi empiriaApi, EventsBus eventsBus,
                                         @ModuleScoped ExternalStateSetter externalStateSetter) {
        this.stateEncoder = stateEncoder;
        this.view = view;
        this.stateSaver = stateSaver;
        this.externalPaths = externalPaths;
        this.empiriaApi = empiriaApi;
        this.eventsBus = eventsBus;
        this.externalStateSetter = externalStateSetter;

        externalApi = new ExternalApiNullObject();
    }

    @PostConstruct
    public void addHandlers() {
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), updateFrameUrlForIE11Hack);
    }

    public void init() {
        String url = externalPaths.getExternalEntryPointPath();
        view.init(empiriaApi, this, url);
    }

    @Override
    public void onExternalModuleLoaded(ExternalApi externalObject) {
        this.externalApi = externalObject;
        externalStateSetter.setSavedStateInExternal(externalObject);
    }

    public Widget getView() {
        return view.asWidget();
    }

    public JSONArray getState() {
        JavaScriptObject state = externalApi.getStateFromExternal();
        stateSaver.setExternalState(state);
        return stateEncoder.encodeState(state);
    }

    public void setState(JSONArray newState) {
        JavaScriptObject state = stateEncoder.decodeState(newState);
        stateSaver.setExternalState(state);
    }

    public void lock() {
        externalApi.lock();
    }

    public void unlock() {
        externalApi.unlock();
    }

    public void reset() {
        externalApi.reset();
    }

}
