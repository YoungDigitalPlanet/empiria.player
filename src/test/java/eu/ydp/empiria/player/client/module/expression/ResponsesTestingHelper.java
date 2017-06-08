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

package eu.ydp.empiria.player.client.module.expression;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;

public class ResponsesTestingHelper {

    public Response getResponse(String identifier, String value) {
        return new ResponseBuilder().withIdentifier(identifier).withCurrentUserAnswers(value).build();
    }

    public Response getResponse(String identifier, String userAnswer, String correctAnswer) {
        CorrectAnswers answer = new CorrectAnswers();
        answer.add(new ResponseValue(correctAnswer));
        return new ResponseBuilder().withIdentifier(identifier).withCurrentUserAnswers(userAnswer).withCorrectAnswers(correctAnswer).build();
    }
}
