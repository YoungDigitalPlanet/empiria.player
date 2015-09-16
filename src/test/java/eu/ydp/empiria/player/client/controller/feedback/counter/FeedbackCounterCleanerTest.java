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