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
import eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HintInfoJUnitTest extends AbstractTestBase {

    @Test
    public void shouldCreateHints() {
        AssessmentReportFactory factory = injector.getInstance(AssessmentReportFactory.class);
        VariableProviderSocket variableProvider = getVariableProvider();
        HintInfo hints = factory.getHintInfo(variableProvider);

        assertThat("checks", hints.getChecks(), is(equalTo(3)));
        assertThat("mistakes", hints.getMistakes(), is(equalTo(4)));
        assertThat("showAnswers", hints.getShowAnswers(), is(equalTo(5)));
        assertThat("reset", hints.getReset(), is(equalTo(6)));
    }

    private VariableProviderSocket getVariableProvider() {
        VariableProviderSocket variableProvider = mock(VariableProviderSocket.class);
        OutcomeCreator crator = new OutcomeCreator();

        when(variableProvider.getVariableValue(FlowActivityVariablesProcessor.CHECKS)).thenReturn(crator.createTodoOutcome(3));
        when(variableProvider.getVariableValue(VariableName.MISTAKES.toString())).thenReturn(crator.createDoneOutcome(4));
        when(variableProvider.getVariableValue(FlowActivityVariablesProcessor.SHOW_ANSWERS)).thenReturn(crator.createErrorsOutcome(5));
        when(variableProvider.getVariableValue(FlowActivityVariablesProcessor.RESET)).thenReturn(crator.createErrorsOutcome(6));

        return variableProvider;
    }

}
