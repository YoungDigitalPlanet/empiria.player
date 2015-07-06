package eu.ydp.empiria.player.client.controller.session;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.reset.*;
import eu.ydp.gwtutil.client.event.EventImpl.Type;

@RunWith(MockitoJUnitRunner.class)
public class LessonStateResetTest {
	@InjectMocks
	private LessonStateReset testObj;

	@Mock
	private SessionDataManager sessionDataManager;
	@Mock
	private EventsBus eventsBus;
	

	@Test
	public void shouldAddHandler() {
		// given
		Type<LessonResetEventHandler, LessonResetEventTypes> eventType = LessonResetEvent.getType(LessonResetEventTypes.RESET);

		// than
		verify(eventsBus).addHandler(eventType, testObj);
	}

	@Test
	public void shouldResetLessonsState() {
		// given
		LessonResetEvent event = mock(LessonResetEvent.class);
		
		// when
		testObj.onLessonReset(event);

		// than
		verify(sessionDataManager).resetLessonsState();
	}

}
