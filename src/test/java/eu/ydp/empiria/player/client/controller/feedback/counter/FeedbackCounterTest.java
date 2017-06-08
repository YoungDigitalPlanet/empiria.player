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

package eu.ydp.empiria.player.client.controller.feedback.counter;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.feedback.counter.event.FeedbackCounterEvent;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackCounterTest {
    @InjectMocks
    private FeedbackCounter testObj;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private FeedbackCounterCleaner counterRemovable;
    @Mock
    private FeedbackCountable feedbackCountable;
    @Mock
    private FeedbackCountable anotherFeedbackCountable;

    @Test
    public void shouldReturnZero_whenConditionIsNotPresent() {
        // given
        int expected = 0;

        // when
        int result = testObj.getCount(feedbackCountable);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnZero_whenAddedAnotherConditionOccurrence() {
        // given
        int expected = 0;

        // when
        testObj.add(anotherFeedbackCountable);
        int result = testObj.getCount(feedbackCountable);

        // then
        assertThat(result).isEqualTo(expected);
    }
    @Test
    public void shouldReturnOne_whenConditionOccurrenceAddedOnce(){
        // given
        int expected = 1;

        // when
        testObj.add(feedbackCountable);
        int result = testObj.getCount(feedbackCountable);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnTwo_whenConditionOccurrenceAddedTwice(){
        // given
        int expected = 2;

        // when
        testObj.add(feedbackCountable);
        testObj.add(feedbackCountable);
        int result = testObj.getCount(feedbackCountable);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnOne_whenConditionOccurrenceAddedOnce_andAnotherConditionAddedTwice(){
        // given
        int expected = 1;

        // when
        testObj.add(feedbackCountable);
        testObj.add(anotherFeedbackCountable);
        testObj.add(anotherFeedbackCountable);
        int result = testObj.getCount(feedbackCountable);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnZero_afterEventFired(){
        // given
        int expected = 0;
        testObj.add(feedbackCountable);

        FeedbackCounterEvent event = mock(FeedbackCounterEvent.class);
        IModule module = mock(IModule.class);
        when(event.getSourceModule()).thenReturn(module);
        when(counterRemovable.getObjectsToRemove(module)).thenReturn(Lists.newArrayList(feedbackCountable));

        // when
        testObj.onFeedbackCounterEvent(event);
        int result = testObj.getCount(feedbackCountable);

        // then
        assertThat(result).isEqualTo(expected);
    }
}