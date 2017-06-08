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

import eu.ydp.empiria.player.client.compressor.LzGwtWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmpiriaStateExportCreatorTest {

    @InjectMocks
    private EmpiriaStateExportCreator testObj;
    @Mock
    private LzGwtWrapper lzGwtWrapper;
    @Mock
    private LessonIdentifierProvider lessonIdentifierProvider;

    @Test
    public void shouldCreateCompressedState() throws Exception {
        // GIVEN
        String givenState = "given state";
        String expectedState = "compressed";
        String id = "id";

        when(lzGwtWrapper.compress(givenState)).thenReturn(expectedState);
        when(lessonIdentifierProvider.getLessonIdentifier()).thenReturn(id);

        // WHEN
        EmpiriaState result = testObj.create(givenState);

        // THEN
        assertThat(result.getFormatType()).isEqualTo(EmpiriaStateType.LZ_GWT);
        assertThat(result.getState()).isEqualTo(expectedState);
        assertThat(result.getLessonIdentifier()).isEqualTo(id);
    }
}