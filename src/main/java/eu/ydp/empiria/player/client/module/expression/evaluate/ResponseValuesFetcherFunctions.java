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

package eu.ydp.empiria.player.client.module.expression.evaluate;

import com.google.common.base.Function;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

import javax.annotation.Nullable;

public class ResponseValuesFetcherFunctions {

    private final Function<Response, String> userAnswerFetcher = new Function<Response, String>() {

        @Override
        @Nullable
        public String apply(@Nullable Response response) {
            return response.values.get(0);
        }
    };

    private final Function<Response, String> correctAnswerFetcher = new Function<Response, String>() {

        @Override
        @Nullable
        public String apply(@Nullable Response response) {
            return response.correctAnswers.getSingleAnswer();
        }
    };

    public Function<Response, String> getUserAnswerFetcher() {
        return userAnswerFetcher;
    }

    public Function<Response, String> getCorrectAnswerFetcher() {
        return correctAnswerFetcher;
    }
}
