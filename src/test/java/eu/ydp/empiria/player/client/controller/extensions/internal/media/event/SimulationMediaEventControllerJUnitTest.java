package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
