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

import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackCounterCleanerTest {

    @InjectMocks
    private FeedbackCounterCleaner testObj;
    @Mock
    private FeedbackRegistry feedbackRegistry;
    @Mock
    private Feedback feedback;
    @Mock
    private FeedbackAction feedbackAction;
    @Mock
    private FeedbackCondition feedbackCondition;
    @Mock
    private IModule module;

    private List<Feedback> feedbacks = new ArrayList<>();
    private List<FeedbackAction> feedbackActions = new ArrayList<>();

    @Before
    public void init() {
        feedbacks.add(feedback);
        feedbackActions.add(feedbackAction);

        when(feedback.getActions()).thenReturn(feedbackActions);
        when(feedback.getCondition()).thenReturn(feedbackCondition);

        when(feedbackRegistry.getModuleFeedbacks(module)).thenReturn(feedbacks);
    }

    @Test
    public void shouldMarkCondition_andAction_toRemove() {
        // when
        Collection<FeedbackCountable> objectsToRemove = testObj.getObjectsToRemove(module);

        // then
        assertThat(objectsToRemove).contains(feedbackAction);
        assertThat(objectsToRemove).contains(feedbackCondition);
    }
}