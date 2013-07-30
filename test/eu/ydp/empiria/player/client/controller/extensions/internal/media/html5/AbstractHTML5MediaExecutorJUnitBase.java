package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.media.client.MediaBase;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

@SuppressWarnings("PMD")
public abstract class AbstractHTML5MediaExecutorJUnitBase extends AbstractTestBaseWithoutAutoInjectorInit {


	protected AbstractHTML5MediaExecutor<MediaBase> instance;

	protected MediaBase mediaBase;

	protected BaseMediaConfiguration mediaConfiguration;

	protected HandlerRegistration handlerRegistration =  mock(HandlerRegistration.class);

	protected EventsBus eventsBus;


	public abstract MediaBase getMediaBaseMock();

	public abstract AbstractHTML5MediaExecutor getExecutorInstanceMock();

	public abstract BaseMediaConfiguration getBaseMediaConfiguration();

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}


	public void before() {
		instance = getExecutorInstanceMock();
		mediaBase = getMediaBaseMock();
		mediaConfiguration = getBaseMediaConfiguration();
		doReturn(handlerRegistration).when(mediaBase).addBitlessDomHandler(Mockito.any(HTML5MediaEventHandler.class), Mockito.any(Type.class));
		eventsBus = injector.getInstance(EventsBus.class);
		instance.setMedia(mediaBase);
	}

	public void setUpGuice() {
		GuiceModuleConfiguration configuration = new GuiceModuleConfiguration();
		configuration.addAllClassToSpy(EventsBus.class);
		setUp(configuration);
	}

	@Test
	public void testInitNoConfiguration() {
		mediaConfiguration = new BaseMediaConfiguration(new HashMap<String, String>(), false);
		instance.setBaseMediaConfiguration(mediaConfiguration);
		instance.init();
		verify(mediaBase).setPreload(Mockito.eq(MediaElement.PRELOAD_METADATA));
		verify(mediaBase).setControls(Mockito.eq(true));
	}

	@Test
	public void testInit() {
		instance.setBaseMediaConfiguration(mediaConfiguration);
		instance.init();
		verify(mediaBase).setPreload(Mockito.eq(MediaElement.PRELOAD_METADATA));
		verify(mediaBase).setControls(Mockito.eq(false));
	}

	@Test
	public void testInitMediaNotSet() {
		instance.setMedia(null);
		instance.init();
		verify(instance).init();
		verify(instance,times(2)).setMedia(Mockito.any(MediaBase.class));
		verifyNoMoreInteractions(instance);
	}

	@Test
	public void testRemoveRegistration() {
		instance.setBaseMediaConfiguration(mediaConfiguration);
		instance.init();
		instance.init();
		verify(handlerRegistration).removeHandler();
	}


	@Test
	public void testSetMediaWrapper() {
		instance.setMedia(null);
		MediaWrapper<MediaBase> mediaWrapper = mock(MediaWrapper.class);
		doReturn(mediaBase).when(mediaWrapper).getMediaObject();
		instance.setMediaWrapper(mediaWrapper);
		assertEquals(mediaBase, instance.getMedia());
		assertEquals(mediaWrapper, instance.getMediaWrapper());
	}

	@Test
	public void testSetBaseMediaConfiguration() {
		instance.setBaseMediaConfiguration(mediaConfiguration);
		assertEquals(mediaConfiguration, instance.getBaseMediaConfiguration());
	}

	@Test
	public void testOnEvent() {

		MediaWrapper<MediaBase> mediaWrapper = mock(MediaWrapper.class);
		doReturn(mediaBase).when(mediaWrapper).getMediaObject();
		Map<HTML5MediaEventsType, MediaEventTypes> pairMap = creatEventsPairMap();

		Set<HTML5MediaEventsType> asyncEvents = Sets.newHashSet(HTML5MediaEventsType.durationchange,HTML5MediaEventsType.timeupdate);

		instance.setMediaWrapper(mediaWrapper);
		for(Map.Entry<HTML5MediaEventsType, MediaEventTypes> typePair : pairMap.entrySet()){
			ArgumentCaptor<MediaEvent> eventCaptor  = ArgumentCaptor.forClass(MediaEvent.class);
			HTML5MediaEvent event = mock(HTML5MediaEvent.class);
			doReturn(typePair.getKey()).when(event).getType();
			instance.onEvent(event);
			if(asyncEvents.contains(typePair.getKey())) {
				verify(eventsBus).fireAsyncEventFromSource(eventCaptor.capture(), Mockito.eq(mediaWrapper));
			}else{
				verify(eventsBus).fireEventFromSource(eventCaptor.capture(), Mockito.eq(mediaWrapper));

			}
			verifyNoMoreInteractions(eventsBus);
			assertEquals(typePair.getValue(), eventCaptor.getValue().getType());
			Mockito.reset(eventsBus);
		}
	}

	@Test
	public void testOnEventWithDisabledEventPropagationForPlay(){
		MediaWrapper<MediaBase> mediaWrapper = mock(MediaWrapper.class);
		doReturn(mediaBase).when(mediaWrapper).getMediaObject();
		instance.playWithoutOnPlayEventPropagation();
		HTML5MediaEvent event = mock(HTML5MediaEvent.class);
		doReturn(HTML5MediaEventsType.play).when(event).getType();
		instance.onEvent(event);
		verifyZeroInteractions(eventsBus);
	}

	@Test
	public void testOnEventSoundExecutorListener() {

		Map<HTML5MediaEventsType, MediaEventTypes> pairMap = creatEventsPairMap();
		SoundExecutorListener soundExecutorListener = mock(SoundExecutorListener.class);
		Set<HTML5MediaEventsType> listenerEvents = Sets.newHashSet(HTML5MediaEventsType.ended,HTML5MediaEventsType.play);

		instance.setSoundFinishedListener(soundExecutorListener);
		for(HTML5MediaEventsType type : pairMap.keySet()){
			HTML5MediaEvent event = mock(HTML5MediaEvent.class);
			doReturn(type).when(event).getType();
			instance.onEvent(event);

			if(listenerEvents.contains(type)){
				if(type==HTML5MediaEventsType.play){
					verify(soundExecutorListener).onPlay();
				}else{
					verify(soundExecutorListener).onSoundFinished();
				}
			}else{
				verifyZeroInteractions(soundExecutorListener);
			}

			Mockito.reset(soundExecutorListener);
		}
	}

	@Test
	public void testOnUnsupportedEvent() {
		Map<HTML5MediaEventsType, MediaEventTypes> pairMap = creatEventsPairMap();
		Set<HTML5MediaEventsType> unsupportedTypes = Sets.newHashSet(HTML5MediaEventsType.values());
		unsupportedTypes.removeAll(pairMap.keySet());

		for(HTML5MediaEventsType type : unsupportedTypes){
			HTML5MediaEvent event = mock(HTML5MediaEvent.class);
			doReturn(type).when(event).getType();
			instance.onEvent(event);
			verifyZeroInteractions(eventsBus);
		}
	}

	private Map<HTML5MediaEventsType, MediaEventTypes> creatEventsPairMap() {
		Map<HTML5MediaEventsType, MediaEventTypes> pairMap = ImmutableMap.<HTML5MediaEventsType, MediaEventTypes>builder().
		put(HTML5MediaEventsType.canplay,MediaEventTypes.CAN_PLAY).
		put(HTML5MediaEventsType.suspend,MediaEventTypes.SUSPEND).
		put(HTML5MediaEventsType.durationchange,MediaEventTypes.ON_DURATION_CHANGE).
		put(HTML5MediaEventsType.ended,MediaEventTypes.ON_END).
		put(HTML5MediaEventsType.error,MediaEventTypes.ON_ERROR).
		put(HTML5MediaEventsType.pause,MediaEventTypes.ON_PAUSE).
		put(HTML5MediaEventsType.timeupdate,MediaEventTypes.ON_TIME_UPDATE).
		put(HTML5MediaEventsType.volumechange,MediaEventTypes.ON_VOLUME_CHANGE).
		put(HTML5MediaEventsType.play,MediaEventTypes.ON_PLAY).build();
		return pairMap;
	}


	@Test
	public void testSetMedia() {
		assertEquals(mediaBase, instance.getMedia());
	}

	@Test
	public void testPlay() {
		instance.play();
		verify(mediaBase).play();
	}

	@Test
	public void testPlayWithString() {
		instance.play("");
		verify(mediaBase).play();
	}

	@Test
	public void testStop() {
		instance.stop();
		verify(mediaBase).pause();
		verify(mediaBase).setCurrentTime(Mockito.eq(0d));

	}

	@Test
	public void testPause() {
		instance.pause();
		verify(mediaBase).pause();
	}

	@Test
	public void testSetMuted() {
		instance.setMuted(true);
		verify(mediaBase).setMuted(Mockito.eq(true));
		instance.setMuted(false);
		verify(mediaBase).setMuted(Mockito.eq(false));

	}

	@Test
	public void testSetVolume() {
		instance.setVolume(3);
		verify(mediaBase).setVolume(Mockito.eq(3d));
		instance.setVolume(5.6);
		verify(mediaBase).setVolume(Mockito.eq(5.6d));
	}

	@Test
	public void testSetCurrentTime() {
		instance.setCurrentTime(3);
		verify(mediaBase).setCurrentTime(Mockito.eq(3d));
	}

	@Test
	public void testSetCurrentTimeNAN() {
		doReturn(Double.NaN).when(mediaBase).getDuration();
		instance.setCurrentTime(3);
		verify(mediaBase,times(0)).setCurrentTime(Mockito.anyDouble());
	}


}
