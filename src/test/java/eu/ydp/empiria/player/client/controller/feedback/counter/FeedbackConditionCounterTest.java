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
    private FeedbackCondition firstCondition;
    @Mock
    private FeedbackCondition secondCondition;

    @Test
    public void shouldReturnZero_whenIsNotPresent() {
        // given
        int expected = 0;

        // when
        int result = testObj.getCount(firstCondition);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnZero_whenAddedAnother() {
        // given
        int expected = 0;

        // when
        testObj.add(secondCondition);
        int result = testObj.getCount(firstCondition);

        // then
        assertThat(result).isEqualTo(expected);
    }
    @Test
    public void shouldReturnOne_whenAddedOnce(){
        // given
        int expected = 1;

        // when
        testObj.add(firstCondition);
        int result = testObj.getCount(firstCondition);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnTwo_whenAddedTwice(){
        // given
        int expected = 2;

        // when
        testObj.add(firstCondition);
        testObj.add(firstCondition);
        int result = testObj.getCount(firstCondition);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnOne_whenAddedOnce_andAnother(){
        // given
        int expected = 1;

        // when
        testObj.add(firstCondition);
        testObj.add(secondCondition);
        int result = testObj.getCount(firstCondition);

        // then
        assertThat(result).isEqualTo(expected);
    }
}