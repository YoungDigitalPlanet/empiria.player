package eu.ydp.empiria.player.client.controller.feedback.counter;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackConditionCounterTest {

    @InjectMocks
    private FeedbackConditionCounter testObj;
    @Mock
    private FeedbackRegistry feedbackRegistry;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private FeedbackCondition condition;
    @Mock
    private FeedbackCondition anotherCondition;

    @Test
    public void shouldReturnZero_whenConditionIsNotPresent() {
        // given
        int expected = 0;

        // when
        int result = testObj.getCount(condition);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnZero_whenAddedAnotherConditionOccurrence() {
        // given
        int expected = 0;

        // when
        testObj.add(anotherCondition);
        int result = testObj.getCount(condition);

        // then
        assertThat(result).isEqualTo(expected);
    }
    @Test
    public void shouldReturnOne_whenConditionOccurrenceAddedOnce(){
        // given
        int expected = 1;

        // when
        testObj.add(condition);
        int result = testObj.getCount(condition);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnTwo_whenConditionOccurrenceAddedTwice(){
        // given
        int expected = 2;

        // when
        testObj.add(condition);
        testObj.add(condition);
        int result = testObj.getCount(condition);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnOne_whenConditionOccurrenceAddedOnce_andAnotherConditionAddedTwice(){
        // given
        int expected = 1;

        // when
        testObj.add(condition);
        testObj.add(anotherCondition);
        testObj.add(anotherCondition);
        int result = testObj.getCount(condition);

        // then
        assertThat(result).isEqualTo(expected);
    }
}