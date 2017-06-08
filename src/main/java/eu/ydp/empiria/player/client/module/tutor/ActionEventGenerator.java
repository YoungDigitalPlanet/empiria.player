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

package eu.ydp.empiria.player.client.module.tutor;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.empiria.player.client.module.tutor.actions.OutcomeDrivenActionTypeGenerator;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ActionEventGenerator {

    @Inject
    @ModuleScoped
    private ActionExecutorService executorService;

    @Inject
    @ModuleScoped
    private OutcomeDrivenActionTypeGenerator actionTypeGenerator;

    @Inject
    @ModuleScoped
    private PersonaService personaService;

    @Inject
    @ModuleScoped
    private TutorEndHandler tutorEndHandler;

    public void start() {
        executeDefaultAction();
    }

    public void stop() {
        executeDefaultAction();
    }

    public void stateChanged(EndHandler endHandler) {
        tutorEndHandler.setEndHandler(null);
        TutorPersonaProperties currentPersona = personaService.getPersonaProperties();
        if (currentPersona.isInteractive()) {
            generateAndExecuteAction(endHandler);
        }
    }

    private void generateAndExecuteAction(EndHandler endHandler) {
        Optional<ActionType> actionType = actionTypeGenerator.findActionType();
        if (actionType.isPresent()) {
            tutorEndHandler.setEndHandler(endHandler);
            executeAction(actionType.get());
        }
    }

    public void tutorChanged(int personaIndex) {
        personaService.setCurrentPersonaIndex(personaIndex);
        executeDefaultAction();
    }

    public void executeDefaultAction() {
        executeAction(ActionType.DEFAULT);
    }

    private void executeAction(ActionType action) {
        executorService.execute(action, tutorEndHandler);
    }
}
