package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import static org.mockito.Mockito.*;
import junitparams.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.*;
import eu.ydp.empiria.player.client.util.events.internal.media.*;

@RunWith(JUnitParamsRunner.class)
public class SimulationMediaEventControllerJUnitTest {

	@InjectMocks
	private SimulationMediaEventController testObj;

	@Mock
	private DefaultMediaEventController defaultMediaEventController;
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private MediaEvent event;
	@Mock
	private MediaExecutor<?> executor;
	@Mock
	private AbstractMediaProcessor processor;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Parameters
	public void shouldDelegateEventsToDefaultController(MediaEventTypes type) {
		// when
		when(event.getAssociatedType().getType()).thenReturn(type);
		testObj.onMediaEvent(event, executor, processor);

		// then
		verify(defaultMediaEventController).onMediaEvent(event, executor, processor);
	}

	public Object[] parametersForShouldDelegateEventsToDefaultController() {
		return MediaEventTypes.values();
	}
}
