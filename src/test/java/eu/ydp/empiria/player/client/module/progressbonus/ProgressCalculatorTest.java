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

package eu.ydp.empiria.player.client.module.progressbonus;

import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class ProgressCalculatorTest {

    @InjectMocks
    private ProgressCalculator progressCalculator;
    @Mock
    private OutcomeAccessor accessor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Parameters({"0", "25", "100"})
    public void getProgress(int RESULT) {
        // given
        when(accessor.getAssessmentResult()).thenReturn(RESULT);

        // when
        int progress = progressCalculator.getProgress();

        // then
        assertThat(progress).isEqualTo(RESULT);
    }
}
