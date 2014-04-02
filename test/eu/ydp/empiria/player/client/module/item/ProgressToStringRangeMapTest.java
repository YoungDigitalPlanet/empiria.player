package eu.ydp.empiria.player.client.module.item;

import com.google.common.collect.Range;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ProgressToStringRangeMapTest {

	private static final String FEEDBACK_FROM_0 = "feedbackFrom0";
	private static final String FEEDBACK_FROM_50 = "feedbackFrom50";
	private static final String FEEDBACK_FROM_100 = "feedbackFrom100";

	private ProgressToStringRangeMap testObj;

	@Before
	public void setUp() {
		testObj = new ProgressToStringRangeMap();
	}

	@Test
	public void shouldGetCorrectValueWhenFullCoverage() {
		// given
		testObj.addValueForRange(Range.closed(0, 49), FEEDBACK_FROM_0);
		testObj.addValueForRange(Range.closed(50, 99), FEEDBACK_FROM_50);
		testObj.addValueForRange(Range.closed(100, 100), FEEDBACK_FROM_100);

		// when
		String valueFor0 = testObj.getValueForProgress(0);
		String valueFor50 = testObj.getValueForProgress(50);
		String valueFor55 = testObj.getValueForProgress(55);
		String valueFor100 = testObj.getValueForProgress(100);

		// then
		assertThat(valueFor0).isEqualTo(FEEDBACK_FROM_0);
		assertThat(valueFor50).isEqualTo(FEEDBACK_FROM_50);
		assertThat(valueFor55).isEqualTo(FEEDBACK_FROM_50);
		assertThat(valueFor100).isEqualTo(FEEDBACK_FROM_100);
	}

	@Test
	public void shouldGetCorrectValueWhenPartialCoverage() {
		// given
		testObj.addValueForRange(Range.closed(0, 49), FEEDBACK_FROM_0);
		testObj.addValueForRange(Range.closed(100, 100), FEEDBACK_FROM_100);

		// when
		String valueFor0 = testObj.getValueForProgress(0);
		String valueFor50 = testObj.getValueForProgress(50);
		String valueFor100 = testObj.getValueForProgress(100);

		// then
		assertThat(valueFor0).isEqualTo(FEEDBACK_FROM_0);
		assertThat(valueFor50).isEmpty();
		assertThat(valueFor100).isEqualTo(FEEDBACK_FROM_100);
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
