package eu.ydp.empiria.player.client.controller.session;

import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.reset.LessonResetEvent;
import eu.ydp.empiria.player.client.util.events.internal.reset.LessonResetEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.reset.LessonResetEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        EventType<LessonResetEventHandler, LessonResetEventTypes> eventType = LessonResetEvent.getType(LessonResetEventTypes.RESET);

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
