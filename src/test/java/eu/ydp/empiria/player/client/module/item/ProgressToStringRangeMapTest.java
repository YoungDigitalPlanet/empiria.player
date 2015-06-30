package eu.ydp.empiria.player.client.module.item;

import com.google.common.collect.Range;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ProgressToStringRangeMapTest {

    private static final String FEEDBACK_FROM_0 = "feedbackFrom0";
    private static final String FEEDBACK_FROM_100 = "feedbackFrom100";

    private ProgressToStringRangeMap testObj;

    @Before
    public void setUp() {
        testObj = new ProgressToStringRangeMap();
    }


    private Object[] valuesForFullCoverage() {
        return $(
                $(0, FEEDBACK_FROM_0),
                $(99, FEEDBACK_FROM_0),
                $(100, FEEDBACK_FROM_100)
        );
    }

    @Test
    @Parameters(method = "valuesForFullCoverage")
    public void shouldGetCorrectValueWhenFullCoverage(int result, String feedback) {
        // given
        testObj.addValueForRange(Range.closed(0, 99), FEEDBACK_FROM_0);
        testObj.addValueForRange(Range.closed(100, 100), FEEDBACK_FROM_100);

        // when
        String value = testObj.getValueForProgress(result);

        // then
        assertThat(value).isEqualTo(feedback);
    }


    private Object[] valuesForPartialCoverage() {
        return $(
                $(0, FEEDBACK_FROM_0),
                $(50, ""),
                $(100, FEEDBACK_FROM_100)
        );
    }

    @Test
    @Parameters(method = "valuesForPartialCoverage")
    public void shouldGetCorrectValueWhenPartialCoverage(int result, String feedback) {
        // given
        testObj.addValueForRange(Range.closed(0, 49), FEEDBACK_FROM_0);
        testObj.addValueForRange(Range.closed(100, 100), FEEDBACK_FROM_100);

        // when
        String value = testObj.getValueForProgress(result);

        // then
        assertThat(value).isEqualTo(feedback);
    }

    @Test
    public void shouldGetEmptyValueWhenTooSmall() {
        // when
        String value = testObj.getValueForProgress(-1);

        // then
        assertThat(value).isEmpty();
    }

    @Test
    public void shouldGetEmptyValueWhenTooBig() {
        // when
        String value = testObj.getValueForProgress(101);

        // then
        assertThat(value).isEmpty();
    }
}
