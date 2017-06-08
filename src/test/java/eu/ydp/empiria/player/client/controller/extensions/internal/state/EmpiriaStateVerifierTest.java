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

package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmpiriaStateVerifierTest {

    @InjectMocks
    private EmpiriaStateVerifier testObj;
    @Mock
    private LessonIdentifierProvider lessonIdentifierProvider;

    @Test
    public void shouldReturnGivenState_whenIdentifierIsNull() throws Exception {
        // given
        String nullIdentifier = null;
        EmpiriaState empiriaState = new EmpiriaState(EmpiriaStateType.LZ_GWT, "", nullIdentifier);

        // when
        EmpiriaState result = testObj.verifyState(empiriaState);

        // then
        assertThat(result).isSameAs(empiriaState);
    }

    @Test
    public void shouldReturnGivenState_whenIdentifierIsEmpty() throws Exception {
        // given
        String emptyIdentifier = "";
        EmpiriaState empiriaState = new EmpiriaState(EmpiriaStateType.LZ_GWT, "", emptyIdentifier);

        // when
        EmpiriaState result = testObj.verifyState(empiriaState);

        // then
        assertThat(result).isSameAs(empiriaState);
    }

    @Test
    public void shouldReturnGivenState_whenIdentifiersAreEquals() throws Exception {
        // given
        String identifier = "id";
        EmpiriaState empiriaState = new EmpiriaState(EmpiriaStateType.LZ_GWT, "", identifier);
        when(lessonIdentifierProvider.getLessonIdentifier()).thenReturn(identifier);

        // when
        EmpiriaState result = testObj.verifyState(empiriaState);

        // then
        assertThat(result).isSameAs(empiriaState);
    }

    @Test
    public void shouldReturnUnknownState_whenIdentifiersAreNotEquals() throws Exception {
        // given
        String identifier = "id";
        EmpiriaState empiriaState = new EmpiriaState(EmpiriaStateType.LZ_GWT, "", identifier);
        when(lessonIdentifierProvider.getLessonIdentifier()).thenReturn("anotherIdentifier");

        // when
        EmpiriaState result = testObj.verifyState(empiriaState);

        // then
        assertThat(result.getFormatType()).isEqualTo(EmpiriaStateType.UNKNOWN);
        assertThat(result.getLessonIdentifier()).isEqualTo("anotherIdentifier");
    }
}