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

import eu.ydp.empiria.player.client.controller.data.AssessmentDataSourceManager;
import eu.ydp.gwtutil.client.Base64Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LessonIdentifierProviderTest {

    @InjectMocks
    private LessonIdentifierProvider testObj;
    @Mock
    private AssessmentDataSourceManager assessmentDataSourceManager;
    @Mock
    private Base64Util base64Util;

    @Test
    public void shouldReturnAssessmentTitleEncodedWithBase64() throws Exception {
        // given
        String title = "some title";
        String base64Title = "base64FromTitle";
        when(assessmentDataSourceManager.getAssessmentTitle()).thenReturn(title);
        when(base64Util.encode(title)).thenReturn(base64Title);

        // when
        String lessonIdentifier = testObj.getLessonIdentifier();

        // then
        assertThat(lessonIdentifier).isEqualTo(base64Title);
    }
}