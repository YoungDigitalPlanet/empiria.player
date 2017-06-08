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

package eu.ydp.empiria.player.client.module.external.common.state;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import javax.inject.Inject;

public class ExternalStateSetter {

    private final ExternalStateSaver stateSaver;
    private final ExternalFrameObjectFixer frameObjectFixer;

    @Inject
    public ExternalStateSetter(@ModuleScoped ExternalStateSaver stateSaver, ExternalFrameObjectFixer frameObjectFixer) {
        this.stateSaver = stateSaver;
        this.frameObjectFixer = frameObjectFixer;
    }

    public void setSavedStateInExternal(ExternalApi externalObject) {

        Optional<JavaScriptObject> externalState = stateSaver.getExternalState();
        if (externalState.isPresent()) {
            JavaScriptObject fixedState = frameObjectFixer.fix(externalState.get());
            externalObject.setStateOnExternal(fixedState);
        }
    }
}
