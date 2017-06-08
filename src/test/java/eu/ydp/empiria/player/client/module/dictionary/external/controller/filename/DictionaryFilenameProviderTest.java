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

package eu.ydp.empiria.player.client.module.dictionary.external.controller.filename;

import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.util.NumberFormatWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryFilenameProviderTest {

    private static final int OFFSET = 50;
    private static final String COMMON_PATH = "commons/";
    @InjectMocks
    private DictionaryFilenameProvider testObj;
    @Mock
    private EmpiriaPaths empiriaPaths;
    @Mock
    private NumberFormatWrapper numberFormatWrapper;

    @Test
    public void shouldReturnFilenameWithoffset() {
        // given
        when(empiriaPaths.getCommonsPath()).thenReturn(COMMON_PATH);
        when(numberFormatWrapper.formatNumber(anyString(), eq(OFFSET)))
                .thenReturn("00050");
        String expectedFilePath = "commons/dictionary/explanations/00050.xml";

        // when
        String filePath = testObj.getFilePathForIndex(OFFSET);

        // then
        assertThat(filePath).isEqualTo(expectedFilePath);
    }
}
