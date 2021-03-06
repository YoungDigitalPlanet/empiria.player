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
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import javax.inject.Inject;

public class SingleRunTutorEndHandler implements TutorEndHandler {
    private Optional<EndHandler> endHandler = Optional.absent();

    @Inject
    @ModuleScoped
    private ActionExecutorService executorService;

    @Override
    public void onEnd() {
        fireTheEndHandlerIfPresent();
    }

    @Override
    public void onEndWithDefaultAction() {
        executorService.execute(ActionType.DEFAULT, this);
        fireTheEndHandlerIfPresent();
    }

    private void fireTheEndHandlerIfPresent() {
        if (endHandler.isPresent()) {
            EndHandler endHandlerInstance = endHandler.get();
            endHandler = Optional.absent();
            endHandlerInstance.onEnd();
        }
    }

    @Override
    public void setEndHandler(EndHandler endHandler) {
        this.endHandler = Optional.fromNullable(endHandler);
    }
}
