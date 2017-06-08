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

import com.google.inject.Inject;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ActionExecutorServiceImpl implements ActionExecutorService {

    private CommandFactory commandFactory;
    private TutorCommand currentCommand = null;

    @Inject
    public ActionExecutorServiceImpl(@ModuleScoped CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void execute(ActionType type, TutorEndHandler handler) {
        if (currentCommand != null && !currentCommand.isFinished()) {
            currentCommand.terminate();
        }

        currentCommand = commandFactory.createCommand(type, handler);
        currentCommand.execute();
    }
}
