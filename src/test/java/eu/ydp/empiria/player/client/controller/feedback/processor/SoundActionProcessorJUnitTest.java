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

package eu.ydp.empiria.player.client.controller.feedback.processor;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackUrlAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SoundActionProcessorJUnitTest extends AbstractTestBase {

    private SoundActionProcessor processor;

    @Before
    public void initialize() {
        processor = injector.getInstance(SoundActionProcessor.class);
    }

    @Test
    public void shouldAcceptAction() {
        FeedbackUrlAction action = mock(FeedbackUrlAction.class);
        when(action.getType()).thenReturn(ActionType.NARRATION.getName());

        boolean accepts = processor.canProcessAction(action);
        assertThat(accepts, is(equalTo(true)));
    }

    @Test
    public void shouldNotAcceptActionWhen_isNotUrlAction() {
        ShowTextAction action = mock(ShowTextAction.class);

        boolean accepts = processor.canProcessAction(action);
        assertThat(accepts, is(equalTo(false)));
    }

    @Test
    public void shouldNotAcceptActionWhen_itReturnsAnotherType() {
        FeedbackUrlAction action = mock(FeedbackUrlAction.class);
        when(action.getType()).thenReturn("unknown");

        boolean accepts = processor.canProcessAction(action);
        assertThat(accepts, is(equalTo(false)));

    }

}
