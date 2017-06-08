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

package eu.ydp.empiria.player.client.module.mediator.powerfeedback;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventHandler;

import static eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventTypes.OUTCOME_STATE_CHANGED;

public class PowerFeedbackMediator {

    private PowerFeedbackTutorClient tutor;
    private PowerFeedbackBonusClient bonus;
    private final EndHandler endHandler = new EndHandler() {

        @Override
        public void onEnd() {
            executeBonus();
        }
    };
    private final PlayerEventHandler testPageLoadedHandler = new PlayerEventHandler() {

        @Override
        public void onPlayerEvent(PlayerEvent event) {
            initClients();
        }
    };
    private final StateChangeEventHandler stateChangedHandler = new StateChangeEventHandler() {

        @Override
        public void onStateChange(StateChangeEvent event) {
            StateChangedInteractionEvent scie = event.getValue();
            if (scie.isReset()) {
                resetClients();
            } else if (scie.isUserInteract()) {
                stateChanged();
            }
        }

    };
    private final PlayerEventHandler pageUnloadedhandler = new PlayerEventHandler() {

        @Override
        public void onPlayerEvent(PlayerEvent event) {
            terminate();
        }
    };

    @Inject
    public PowerFeedbackMediator(EventsBus eventsBus, PageScopeFactory pageScopeFactory, PowerFeedbackTutorClient defaultTutorClient,
                                 PowerFeedbackBonusClient defaultBonusClient) {
        this.tutor = defaultTutorClient;
        this.bonus = defaultBonusClient;
        addEventsBusHandlers(eventsBus, pageScopeFactory);
    }

    private void addEventsBusHandlers(EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
        final CurrentPageScope currentPageScope = pageScopeFactory.getCurrentPageScope();
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED), testPageLoadedHandler, currentPageScope);
        eventsBus.addHandler(StateChangeEvent.getType(OUTCOME_STATE_CHANGED), stateChangedHandler, currentPageScope);
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_UNLOADED), pageUnloadedhandler, currentPageScope);
    }

    public void registerTutor(PowerFeedbackTutorClient client) {
        tutor = client;
    }

    public void registerBonus(PowerFeedbackBonusClient client) {
        bonus = client;
    }

    private void stateChanged() {
        tutor.processUserInteraction(endHandler);
    }

    private void executeBonus() {
        bonus.processUserInteraction();
    }

    private void resetClients() {
        tutor.resetPowerFeedback();
        bonus.resetPowerFeedback();
    }

    private void terminate() {
        tutor.terminatePowerFeedback();
        bonus.terminatePowerFeedback();
    }

    private void initClients() {
        tutor.initPowerFeedbackClient();
    }
}
