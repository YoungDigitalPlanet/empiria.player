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

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.OutcomeCreator;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResultInfoJUnitTest extends AbstractTestBase {

    @Test
    public void shouldCreateResult() {
        AssessmentReportFactory factory = injector.getInstance(AssessmentReportFactory.class);
        VariableProviderSocket variableProvider = getVariableProvider();
        ResultInfo result = factory.getResultInfo(variableProvider);

        assertThat("todo", result.getTodo(), is(equalTo(2)));
        assertThat("done", result.getDone(), is(equalTo(1)));
        assertThat("errors", result.getErrors(), is(equalTo(3)));
    }

    private VariableProviderSocket getVariableProvider() {
        VariableProviderSocket variableProvider = mock(VariableProviderSocket.class);
        OutcomeCreator crator = new OutcomeCreator();

        when(variableProvider.getVariableValue(VariableName.TODO.toString())).thenReturn(crator.createTodoOutcome(2));
        when(variableProvider.getVariableValue(VariableName.DONE.toString())).thenReturn(crator.createDoneOutcome(1));
        when(variableProvider.getVariableValue(VariableName.ERRORS.toString())).thenReturn(crator.createErrorsOutcome(3));

        return variableProvider;
    }

}
