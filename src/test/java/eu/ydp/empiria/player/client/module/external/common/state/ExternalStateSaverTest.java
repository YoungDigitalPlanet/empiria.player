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

package eu.ydp.empiria.player.client.module.external.common.state;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ExternalStateSaverTest {

    private ExternalStateSaver testObj = new ExternalStateSaver();

    @Test
    public void shouldReturnEmptyObject_whenStateIsNotSet() {
        // when
        Optional<JavaScriptObject> state = testObj.getExternalState();
        boolean isPresent = state.isPresent();

        // then
        assertThat(isPresent).isFalse();
    }

    @Test
    public void shouldReturnState() {
        // given
        JavaScriptObject expectedState = mock(JavaScriptObject.class);
        testObj.setExternalState(expectedState);

        // when
        Optional<JavaScriptObject> resultState = testObj.getExternalState();

        // then
        assertThat(resultState.isPresent()).isTrue();
        assertThat(resultState.get()).isEqualTo(expectedState);
    }
}
