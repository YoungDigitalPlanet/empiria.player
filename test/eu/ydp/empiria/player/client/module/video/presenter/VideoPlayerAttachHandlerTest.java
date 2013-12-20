package eu.ydp.empiria.player.client.module.video.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.junit.GWTMockUtilities;

import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest(VideoPlayer.class)
public class VideoPlayerAttachHandlerTest {

	@Mock
	private EventsBus eventsBus;
	@Mock
	private VideoPlayer videoPlayer;

	private VideoPlayerAttachHandler testObj;

	@BeforeClass
	public static void before() {
		GWTMockUtilities.disarm();
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		testObj = new VideoPlayerAttachHandler(videoPlayer, eventsBus);
	}

	@Test
	public void shouldRegisterHandlerAtFirstAttachEvent() {
		// given
		AttachEvent attachEvent = mock(AttachEvent.class);
		when(attachEvent.isAttached()).thenReturn(Boolean.TRUE);
		HandlerRegistration handlerRegistration = mock(HandlerRegistration.class);
		when(eventsBus.addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), any(AutoPauseOnPageChangeHandler.class))).thenReturn(
				handlerRegistration);

		// when
		testObj.onAttachOrDetach(attachEvent);

		// then
		verify(eventsBus).addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), any(AutoPauseOnPageChangeHandler.class));
	}

	@Test
	public void shouldRemoveHandlerAtSecondAttachEvent() {
		// given
		HandlerRegistration handlerRegistration = callFirstAttachEvent();
		AttachEvent event = mock(AttachEvent.class);
		when(event.isAttached()).thenReturn(Boolean.FALSE);

		// when
		testObj.onAttachOrDetach(event);

		// then
		verify(handlerRegistration).removeHandler();
	}

	private HandlerRegistration callFirstAttachEvent() {
		AttachEvent attachEvent = mock(AttachEvent.class);
		when(attachEvent.isAttached()).thenReturn(Boolean.TRUE);
		HandlerRegistration handlerRegistration = mock(HandlerRegistration.class);
		when(eventsBus.addHandler(eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE)), any(AutoPauseOnPageChangeHandler.class))).thenReturn(
				handlerRegistration);

		testObj.onAttachOrDetach(attachEvent);

		return handlerRegistration;
	}

	@AfterClass
	public static void after() {
		GWTMockUtilities.restore();
	}

}
