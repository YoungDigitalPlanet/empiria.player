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

package eu.ydp.empiria.player.client.module.info.handler;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.module.item.ProgressToStringRangeMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackValueHandlerTest {

    @InjectMocks
    private FeedbackValueHandler testObj;

    @Mock
    private SessionDataSupplier sessionDataSupplier;
    @Mock
    private DataSourceDataSupplier dataSourceDataSupplier;
    @Mock
    private ResultForPageIndexProvider resultForPageIndexProvider;

    @Mock
    private ContentFieldInfo contentFieldInfo;
    @Mock
    private ProgressToStringRangeMap reportFeedbacks;
    private final String EXPECTED_FEEDBACK = "feedback";
    private int itemIndex = 1;


    @Before
    public void setUp() {
        Integer result = 10;
        when(resultForPageIndexProvider.get(itemIndex)).thenReturn(result);
        when(dataSourceDataSupplier.getItemFeedbacks(itemIndex)).thenReturn(reportFeedbacks);
        when(reportFeedbacks.getValueForProgress(result)).thenReturn(EXPECTED_FEEDBACK);
    }

    @Test
    public void shouldGetValue() {
        // when
        String actualFeedback = testObj.getValue(contentFieldInfo, itemIndex);

        // then
        assertThat(actualFeedback).isEqualTo(EXPECTED_FEEDBACK);
    }
}
