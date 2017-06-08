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

package eu.ydp.empiria.player.client.controller.variables.processor.global;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.*;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class GlobalVariablesTestHelper {

    public static Response createResponse(String id) {
        return getBuilder(id).build();
    }

    public static Response createExpressionResponse(String id, ExpressionBean expression) {
        Response response = getBuilder(id).withExpression(expression).withCheckMode(CheckMode.EXPRESSION).build();
        expression.getResponses().add(response);
        return response;
    }

    private static ResponseBuilder getBuilder(String id) {
        return new ResponseBuilder().withIdentifier(id).withCardinality(Cardinality.SINGLE);
    }

    public static DtoModuleProcessingResult prepareProcessingResults(final int todo, final int done, final int errors, final int mistakes,
                                                                     final LastMistaken lastMistaken) {
        DtoModuleProcessingResult processingResult = DtoModuleProcessingResult.fromDefaultVariables();
        processingResult.setConstantVariables(new ConstantVariables(todo));
        processingResult.setGeneralVariables(new GeneralVariables(Lists.<String>newArrayList(), Lists.<Boolean>newArrayList(), errors, done));
        processingResult.setUserInteractionVariables(new UserInteractionVariables(new LastAnswersChanges(), lastMistaken, mistakes));
        return processingResult;
    }
}
