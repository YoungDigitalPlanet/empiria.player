package eu.ydp.empiria.player.client.module.progressbonus;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;

@RunWith(Parameterized.class)
public class ProgressCalculatorTest {

	@InjectMocks
	private ProgressCalculator progressCalculator;
	@Mock
	private OutcomeAccessor accessor;

	private final int RESULT;

	public ProgressCalculatorTest(int result) {
		this.RESULT = result;
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { 0 }, { 20 }, { 99 }, { 100 } };
		return Arrays.asList(data);
	}

	@Test
	public void getProgress() {
		// given
		when(accessor.getAssessmentResult()).thenReturn(RESULT);

		// when
		int progress = progressCalculator.getProgress();

		// then
		assertThat(progress).isEqualTo(RESULT);
	}
}
