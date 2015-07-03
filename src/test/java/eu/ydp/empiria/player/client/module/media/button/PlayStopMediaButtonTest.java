package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.junit.GWTMockUtilities;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayStopMediaButtonTest {

    private PlayStopMediaButton button;

    @Before
    public void setUp() throws Exception {
        button = mock(PlayStopMediaButton.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void pauseExpected() {
        when(button.isActive()).thenReturn(true);

        MediaEvent firedEvent = button.createMediaEvent();

        assertThat(firedEvent.getType(), equalTo(MediaEventTypes.STOP));
    }

    @Test
    public void playExpected() {
        when(button.isActive()).thenReturn(false);

        MediaEvent firedEvent = button.createMediaEvent();

        assertThat(firedEvent.getType(), equalTo(MediaEventTypes.PLAY));
    }

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void restore() {
        GWTMockUtilities.restore();
    }

}
