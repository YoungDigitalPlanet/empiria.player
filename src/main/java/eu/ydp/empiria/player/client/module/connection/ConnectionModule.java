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

package eu.ydp.empiria.player.client.module.connection;

import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.item.ResponseSocket;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenter;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleJAXBParser;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.structure.StateController;
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.module.core.base.AbstractInteractionModule;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.json.YJsonValue;

public class ConnectionModule extends AbstractInteractionModule<ConnectionModuleModel, MatchInteractionBean> {

    @Inject
    private ConnectionModulePresenter presenter;

    @Inject
    private ConnectionModuleStructure connectionStructure;

    @Inject
    private ConnectionModuleFactory connectionModuleFactory;

    @Inject
    private EventsBus eventsBus;
    @Inject
    private StateController stateController;
    @Inject
    private PageScopeFactory pageScopeFactory;

    @Inject
    @PageScoped
    private ResponseSocket responseSocket;

    private ConnectionModuleModel connectionModel;

    @Override
    protected void initalizeModule() {
        connectionModel = connectionModuleFactory.getConnectionModuleModel(getResponse(), this);
        connectionModel.setResponseSocket(responseSocket);
        getResponse().setCountMode(getCountMode());
    }

    @Override
    protected ActivityPresenter<ConnectionModuleModel, MatchInteractionBean> getPresenter() {
        return presenter;
    }

    @Override
    protected ConnectionModuleModel getResponseModel() {
        return connectionModel;
    }

    @Override
    protected AbstractModuleStructure<MatchInteractionBean, ConnectionModuleJAXBParser> getStructure() {
        return connectionStructure;
    }

    @Override
    public void setState(JSONArray stateAndStructure) {
        clearModel();

        YJsonValue convertedStateAndStructure = stateController.updateStateAndStructureVersion(stateAndStructure);
        JSONArray newState = stateController.getResponse(convertedStateAndStructure);

        getResponseModel().setState(newState);

        PlayerEventHandler pageContentResizedEventHandler = new PlayerEventHandler() {
            @Override
            public void onPlayerEvent(PlayerEvent event) {
                presenter.showAnswers(ShowAnswersType.USER);
                fireStateChanged(false, false);
            }
        };
        CurrentPageScope currentPageScope = pageScopeFactory.getCurrentPageScope();
        eventsBus.addAsyncHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CONTENT_GROWN), pageContentResizedEventHandler, currentPageScope);
    }
}
