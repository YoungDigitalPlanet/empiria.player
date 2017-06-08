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

package eu.ydp.empiria.player.client.controller.variables.processor.global.transformation;

import com.google.common.base.Function;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResponseResultVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResultVariables;

import javax.annotation.Nullable;
import java.util.Map.Entry;

public class DefaultResultVariablesTransformation implements Function<Entry<Response, DtoModuleProcessingResult>, ResultVariables> {

    @Override
    @Nullable
    public ResultVariables apply(@Nullable Entry<Response, DtoModuleProcessingResult> input) {
        return new ResponseResultVariables(input.getValue());
    }
}
