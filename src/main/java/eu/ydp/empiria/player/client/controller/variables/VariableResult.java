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

package eu.ydp.empiria.player.client.controller.variables;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import eu.ydp.gwtutil.client.NumberUtils;

public class VariableResult {

    private static final String DEFAULT_VALUE_FOR_INT = "0";

    private int todo;

    private int done;

    @Inject
    public VariableResult(@Assisted VariableProviderSocket variableProvider) {
        VariableUtil util = new VariableUtil(variableProvider);
        int done = NumberUtils.tryParseInt(util.getVariableValue(VariableName.DONE.toString(), DEFAULT_VALUE_FOR_INT));
        int todo = NumberUtils.tryParseInt(util.getVariableValue(VariableName.TODO.toString(), DEFAULT_VALUE_FOR_INT));

        initialize(done, todo);
    }

    public VariableResult(int done, int todo) {
        initialize(done, todo);
    }

    protected void initialize(int done, int todo) {
        this.todo = todo;
        this.done = done;
    }

    public int getResult() {
        int result = 0;
        if (todo != 0) {
            result = done * 100 / todo;
        }
        return result;
    }

}
