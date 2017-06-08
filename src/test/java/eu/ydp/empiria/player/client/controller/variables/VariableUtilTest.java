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

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VariableUtilTest {

    private VariableUtil util;

    @Before
    public void initialize() {
        VariableProviderSocket variableProviderSocket = getVariableProvider();
        util = new VariableUtil(variableProviderSocket);
    }

    @Test
    public void shouldReturnVariableValue() {
        assertThat(util.getVariableValue("notExistingName", "0"), is(equalTo("0")));
        assertThat(util.getVariableValue("existingVariable", "0"), is(equalTo("3")));
    }

    @Test
    public void shouldReturnVariableIntValue() {
        assertThat(util.getVariableIntValue("notExistingName", 0), is(equalTo(0)));
        assertThat(util.getVariableIntValue("existingVariable", 0), is(equalTo(3)));
    }

    private VariableProviderSocket getVariableProvider() {
        VariableProviderSocket variableProviderSocket = mock(VariableProviderSocket.class);
        Variable variable = mock(Variable.class);

        when(variable.getValuesShort()).thenReturn("3");
        when(variableProviderSocket.getVariableValue("existingVariable")).thenReturn(variable);

        return variableProviderSocket;
    }

}
