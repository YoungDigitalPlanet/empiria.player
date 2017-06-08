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

package eu.ydp.empiria.player.client.module.external.interaction;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSaver;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalApiProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExternalInteractionStateProviderTest {

    @InjectMocks
    private ExternalInteractionStateProvider testObj;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ExternalApiProvider externalApiProvider;
    @Mock
    private ExternalStateSaver externalStateSaver;
    @Mock
    private ExternalStateEncoder stateEncoder;

    @Test
    public void shouldReturnEncodedState() throws Exception {
        // GIVEN
        JavaScriptObject givenStateObject = mock(JavaScriptObject.class);
        JSONArray encodedState = mock(JSONArray.class);

        when(externalApiProvider.getExternalApi().getStateFromExternal()).thenReturn(givenStateObject);
        when(stateEncoder.encodeState(givenStateObject)).thenReturn(encodedState);

        // WHEN
        JSONArray result = testObj.getState();

        // THEN
        assertThat(result).isEqualTo(encodedState);
        verify(externalStateSaver).setExternalState(givenStateObject);
    }
}