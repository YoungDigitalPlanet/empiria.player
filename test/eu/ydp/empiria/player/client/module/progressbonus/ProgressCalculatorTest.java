package eu.ydp.empiria.player.client.module.progressbonus;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;

@RunWith(JUnitParamsRunner.class)
public class ProgressCalculatorTest {

	@InjectMocks
	private ProgressCalculator progressCalculator;
	@Mock
	private OutcomeAccessor accessor;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Parameters({"0", "25", "100"})
	public void getProgress(int RESULT) {
		// given
		when(accessor.getAssessmentResult()).thenReturn(RESULT);

		// when
		int progress = progressCalculator.getProgress();

		// then
		assertThat(progress).isEqualTo(RESULT);
	}
}
