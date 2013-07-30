package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Sets;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest(Widget.class)
public class IosAudioPlayHackTest {

	private @Mock EventsBus eventsBus;
	private @Mock HTML5AudioMediaExecutor html5AudioMediaExecutor;
	private @Mock RootPanelDelegate panelDelegate;
	private @Mock RootPanel rootPanel;
	private @Mock Audio audio;
	private @Mock com.google.web.bindery.event.shared.HandlerRegistration eventsBusHandlerRegistration;
	private @InjectMocks IosAudioPlayHack instance;
	private @Mock HandlerRegistration touchHandlerRegistration;
	private @Mock HandlerRegistration nativePlayEventHandlerRegistration;
	private @Captor ArgumentCaptor<MediaEventHandler> mediaEvents;
	private @Captor ArgumentCaptor<Type> typeOfEvents;
	private @Captor ArgumentCaptor<HTML5MediaEventHandler> nativeEventHandlers;
	private final Set<MediaEventTypes> requiredTypes = Sets.newHashSet(MediaEventTypes.PLAY);

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		doReturn(audio).when(html5AudioMediaExecutor).getMedia();
		doReturn(nativePlayEventHandlerRegistration).when(audio).
								addBitlessDomHandler(any(HTML5MediaEventHandler.class),
													 any(com.google.gwt.event.dom.client.DomEvent.Type.class));
		doReturn(rootPanel).when(panelDelegate).getRootPanel();
		doReturn(touchHandlerRegistration).when(rootPanel).addDomHandler(
								any(TouchStartHandler.class),
								any(com.google.gwt.event.dom.client.DomEvent.Type.class));
		doReturn(eventsBusHandlerRegistration).when(eventsBus).addHandlerToSource(any(Type.class), any(MediaWrapper.class), any(MediaEventHandler.class));
	}

	@Test
	public void onTouchStart() throws Exception {
		instance.applyHack(html5AudioMediaExecutor);
		instance.onTouchStart(null);

		verify(eventsBus, times(1)).addHandlerToSource(typeOfEvents.capture(), any(MediaWrapper.class), mediaEvents.capture());

		for (Type type : typeOfEvents.getAllValues()) {
			assertThat(requiredTypes.contains(type.getType())).isTrue();
			requiredTypes.remove(type.getType());
		}

		for (MediaEventHandler handler : mediaEvents.getAllValues()) {
			assertThat(handler).isSameAs(instance);
		}
		verify(audio).addBitlessDomHandler(any(HTML5MediaEventHandler.class),
				 any(com.google.gwt.event.dom.client.DomEvent.Type.class));
		verify(touchHandlerRegistration).removeHandler();
		verify(html5AudioMediaExecutor).playWithoutOnPlayEventPropagation();

	}

	@Test
	public void onMediaEventPlayHackDisabled() throws Exception {
		MediaEvent event = new MediaEvent(MediaEventTypes.ON_PLAY);
		instance.onMediaEvent(event);
		verifyZeroInteractions(html5AudioMediaExecutor);
		verify(touchHandlerRegistration,times(0)).removeHandler();
		verify(eventsBusHandlerRegistration,times(0)).removeHandler();
	}

	@Test
	public void onMediaEventPlayHackEnebled() throws Exception {
		instance.applyHack(html5AudioMediaExecutor);
		instance.onTouchStart(null);
		MediaEvent event = new MediaEvent(MediaEventTypes.ON_PLAY);
		instance.onMediaEvent(event);
		verify(audio).addBitlessDomHandler(nativeEventHandlers.capture(),
				 any(com.google.gwt.event.dom.client.DomEvent.Type.class));

		HTML5MediaEvent nativeEvent = mock(HTML5MediaEvent.class);
		doReturn(HTML5MediaEventsType.play).when(nativeEvent).getType();
		nativeEventHandlers.getValue().onEvent(nativeEvent);

		verify(html5AudioMediaExecutor).stop();
		verify(eventsBusHandlerRegistration,times(1)).removeHandler();
		verify(nativePlayEventHandlerRegistration,times(1)).removeHandler();
	}

	@Test
	public void noInteractionOnOtherMediaEvents(){
		Set<MediaEventTypes> allEvents = Sets.newHashSet(MediaEventTypes.values());
		allEvents.removeAll(requiredTypes);
		for(MediaEventTypes type : allEvents){
			instance.onMediaEvent(new MediaEvent(type));
			verifyZeroInteractions(html5AudioMediaExecutor);
			verifyZeroInteractions(eventsBusHandlerRegistration);
			verifyZeroInteractions(nativePlayEventHandlerRegistration);
			verifyZeroInteractions(eventsBus);

		}
	}
}
