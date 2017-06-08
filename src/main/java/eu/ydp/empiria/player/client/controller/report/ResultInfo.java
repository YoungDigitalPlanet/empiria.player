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

package eu.ydp.empiria.player.client.controller.report;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableResult;
import eu.ydp.empiria.player.client.controller.variables.VariableUtil;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

public class ResultInfo {

    private static final int DEFAULT_INT_VALUE = 0;

    private VariableUtil util;

    @Inject
    public ResultInfo(@Assisted VariableProviderSocket variableProvider) {
        util = new VariableUtil(variableProvider);
    }

    public int getTodo() {
        return util.getVariableIntValue(VariableName.TODO.toString(), DEFAULT_INT_VALUE);
    }

    public int getDone() {
        return util.getVariableIntValue(VariableName.DONE.toString(), DEFAULT_INT_VALUE);
    }

    public int getErrors() {
        return util.getVariableIntValue(VariableName.ERRORS.toString(), DEFAULT_INT_VALUE);
    }

    public int getResult() {
        VariableResult variableResult = new VariableResult(getDone(), getTodo());
        return variableResult.getResult();
    }

}
