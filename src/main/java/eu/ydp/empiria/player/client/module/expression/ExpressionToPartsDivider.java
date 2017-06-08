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

import com.google.common.base.Function;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.exception.CannotDivideExpressionToPartsException;
import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;

import java.util.Arrays;
import java.util.List;

public class ExpressionToPartsDivider {

    public static final String EQUAL_SIGN = "=";
    private static final Logger LOGGER = new Logger();

    public List<String> divideExpressionOnEquality(String template, List<Response> responses, Function<Response, String> answerFetcher) {
        String templateWithEquality = prepareTemplate(template, responses, answerFetcher);
        String[] leftRightExpressionParts = templateWithEquality.split(EQUAL_SIGN);
        LOGGER.severeIf(leftRightExpressionParts.length != 2, "Expression divided on EQUAL sign contains different amount of parts than 2!");
        return Arrays.asList(leftRightExpressionParts);
    }

    private String prepareTemplate(String template, List<Response> responses, Function<Response, String> answerFetcher) {
        String templateWithEquality = template;
        if (!template.contains(EQUAL_SIGN)) {
            templateWithEquality = replaceResponseIdWithEqualitySign(template, responses, answerFetcher);
        }
        return templateWithEquality;
    }

    private String replaceResponseIdWithEqualitySign(String template, List<Response> responses, Function<Response, String> answerFetcher) {
        Response response = findResponseWithAnswerAsEqualSign(responses, answerFetcher);

        String responseId = response.getID();
        String updatedTemplate = template.replace("'" + responseId + "'", EQUAL_SIGN);

        return updatedTemplate;
    }

    private Response findResponseWithAnswerAsEqualSign(List<Response> responses, Function<Response, String> answerFetcher) {
        for (Response response : responses) {
            if (answerFetcher.apply(response).equals(EQUAL_SIGN)) {
                return response;
            }
        }

        throw new CannotDivideExpressionToPartsException("Cannot divide expression on equal sign, template or any response dont contain equal sign!");
    }

}
