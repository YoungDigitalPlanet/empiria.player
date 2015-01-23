package eu.ydp.empiria.player.client.controller.session;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.reset.LessonResetEvent;

@RunWith(MockitoJUnitRunner.class)
public class LessonStateResetTest {
	@InjectMocks
	private LessonStateReset testObj;

	@Mock
	private SessionDataManager sessionDataManager;
	@Mock
	private EventsBus eventsBus;
	

	@Test
	public void test() {
		// given
		LessonResetEvent event = mock(LessonResetEvent.class);
		
		// when
		testObj.onLessonReset(event);

		// than
		verify(sessionDataManager).resetLessonsState();
	}

}
