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

package eu.ydp.empiria.player.client.module.external.interaction.api;

import com.google.gwt.core.client.js.JsType;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.interaction.ExternalInteractionResponseModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

@JsType
public class ExternalInteractionEmpiriaApi extends ExternalEmpiriaApi {

    @Inject
    @ModuleScoped
    private ExternalInteractionResponseModel responseModel;

    public void onResultChange(ExternalInteractionStatus status) {
        int done = status.getDone();
        int errors = status.getErrors();
        responseModel.clearAnswers();

        for (int i = 1; i <= done; i++) {
            responseModel.addAnswer(String.valueOf(i));
        }
        for (int i = 1; i <= errors; i++) {
            responseModel.addAnswer(String.valueOf(-i));
        }
    }


}
