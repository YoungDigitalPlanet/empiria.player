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

import java.util.ArrayList;
import java.util.List;

public class ResponseBeanConverter {

    public Response convert(ResponseBean responseBean) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.withCardinality(responseBean.getCardinality());
        builder.withCheckMode(responseBean.getCheckMode());
        builder.withEvaluate(responseBean.getEvaluate());
        builder.withIdentifier(responseBean.getIdentifier());
        builder.withCompilerCountMode(responseBean.getCountMode());

        CorrectAnswers correctAnswers = getCorrectAnswers(responseBean);
        List<String> groups = getGroups(responseBean);

        builder.withCorrectAnswers(correctAnswers);

        builder.withGroups(groups);

        return builder.build();
    }

    private List<String> getGroups(ResponseBean responseBean) {
        List<String> groups = new ArrayList<String>();
        for (ValueBean valueBean : responseBean.getCorrectResponse().getValues()) {

            if (valueBean.getGroup() != null) {
                groups.add(valueBean.getGroup());
            }
        }
        return groups;
    }

    private CorrectAnswers getCorrectAnswers(ResponseBean responseBean) {
        CorrectAnswers correctAnswers = new CorrectAnswers();

        for (ValueBean valueBean : responseBean.getCorrectResponse().getValues()) {

            correctAnswers.add(new ResponseValue(valueBean.getValue()));
        }
        return correctAnswers;
    }
}
