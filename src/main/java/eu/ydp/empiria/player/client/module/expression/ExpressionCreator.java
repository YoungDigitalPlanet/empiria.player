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

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

import java.util.List;

public class ExpressionCreator {

    public String getExpression(ExpressionBean expressionBean) {
        String template = expressionBean.getTemplate();
        List<Response> responses = expressionBean.getResponses();

        for (Response response : responses) {
            String identifier = getIdentifier(response);
            String value = getValue(response);

            template = template.replace(identifier, value);
        }

        return template;
    }

    private String getIdentifier(Response response) {
        return "'" + response.getID() + "'";
    }

    private String getValue(Response response) {
        List<String> values = response.values;
        return values.get(0);
    }

}
