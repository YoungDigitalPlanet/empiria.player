package eu.ydp.empiria.player.client.controller.feedback.processor;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.*;
import org.junit.*;

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
