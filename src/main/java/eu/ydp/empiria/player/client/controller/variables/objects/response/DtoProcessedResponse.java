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

package eu.ydp.empiria.player.client.controller.variables.objects.response;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;

public class DtoProcessedResponse {

    private Response currentResponse;
    private DtoModuleProcessingResult previousProcessingResult;
    private LastAnswersChanges lastAnswersChanges;

    public DtoProcessedResponse(Response currentResponse, DtoModuleProcessingResult previousProcessingResult, LastAnswersChanges lastAnswerChanges) {
        this.currentResponse = currentResponse;
        this.previousProcessingResult = previousProcessingResult;
        this.lastAnswersChanges = lastAnswerChanges;
    }

    public Response getCurrentResponse() {
        return currentResponse;
    }

    public void setCurrentResponse(Response currentResponse) {
        this.currentResponse = currentResponse;
    }

    public DtoModuleProcessingResult getPreviousProcessingResult() {
        return previousProcessingResult;
    }

    public void setPreviousProcessingResult(DtoModuleProcessingResult previousProcessingResult) {
        this.previousProcessingResult = previousProcessingResult;
    }

    public LastAnswersChanges getLastAnswersChanges() {
        return lastAnswersChanges;
    }

    public void setLastAnswersChanges(LastAnswersChanges lastAnswerChanges) {
        this.lastAnswersChanges = lastAnswerChanges;
    }

    public boolean containChanges() {
        return lastAnswersChanges.containChanges();
    }

}
