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

package eu.ydp.empiria.player.client.module.tutor.actions;

import eu.ydp.empiria.player.client.controller.variables.processor.FeedbackActionConditions;
import eu.ydp.empiria.player.client.module.tutor.ActionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OnPageAllOkActionTest {

    @InjectMocks
    private OnPageAllOkAction action;

    @Mock
    private FeedbackActionConditions actionConditions;

    @Test
    public void actionOccured() {
        // given
        when(actionConditions.isPageAllOk()).thenReturn(true);

        // when
        boolean occured = action.actionOccured();

        // then
        assertThat(occured).isTrue();
    }

    @Test
    public void actionNotOccured() {
        // given
        when(actionConditions.isPageAllOk()).thenReturn(false);

        // when
        boolean occured = action.actionOccured();

        // then
        assertThat(occured).isFalse();
    }

    @Test
    public void actionNotOccured_noActivities() {
        // given
        when(actionConditions.isPageAllOk()).thenReturn(false);

        // when
        boolean occured = action.actionOccured();

        // then
        assertThat(occured).isFalse();
    }

    @Test
    public void type() {
        // when
        ActionType type = action.getActionType();

        // then
        assertThat(type).isEqualTo(ActionType.ON_PAGE_ALL_OK);
    }

}
