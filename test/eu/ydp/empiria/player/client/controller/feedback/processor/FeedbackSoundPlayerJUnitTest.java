package eu.ydp.empiria.player.client.controller.feedback.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.Event.Type;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

@SuppressWarnings("PMD")
public class FeedbackSoundPlayerJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private FeedbackSoundPlayer instance;
	private EventsBus eventsBus;
	private MediaWrapper<?> mediaWrapper;
	private MediaEvent mediaEvent;

	@Before
	public void before() {
		setUp(new Class<?>[] {}, new Class<?>[] {}, new Class<?>[] { EventsBus.class });
		instance = spy(injector.getInstance(FeedbackSoundPlayer.class));
		eventsBus = injector.getInstance(EventsBus.class);
	}

	@Test
	public void testFirePlayEvent() {
		createMediaWrapperMock();
		instance.firePlayEvent(mediaWrapper);

		ArgumentCaptor<MediaEvent> arguments = ArgumentCaptor.forClass(MediaEvent.class);
		verify(eventsBus).fireEventFromSource(arguments.capture(), Mockito.eq(mediaWrapper));

		MediaEvent event = arguments.getValue();
		assertEquals(MediaEventTypes.PLAY, event.getType());
		assertEquals(mediaWrapper, event.getMediaWrapper());
	}

	private void createMediaWrapperMock() {
		mediaWrapper = mock(MediaWrapper.class);
	}

	@Test
	public void testFireStopEvent() {
		createMediaWrapperMock();
		instance.fireStopEvent(mediaWrapper);

		ArgumentCaptor<MediaEvent> arguments = ArgumentCaptor.forClass(MediaEvent.class);
		verify(eventsBus).fireEventFromSource(arguments.capture(), Mockito.eq(mediaWrapper));

		MediaEvent event = arguments.getValue();
		assertEquals(MediaEventTypes.STOP, event.getType());
		assertEquals(mediaWrapper, event.getMediaWrapper());
	}

	@Test
	public void testPlayListOfString() {
		ArgumentCaptor<Map> argumentCaptor = ArgumentCaptor.forClass(Map.class);
		doNothing().when(instance).play(Mockito.any(String.class), argumentCaptor.capture());
		doReturn("test").when(instance).getMimeType(Mockito.anyString());

		List<String> sources = Arrays.asList(new String[] { "ogg", "mp3" });
		instance.play(sources);

		Map argumentCaptorValue = argumentCaptor.getValue();
		verify(instance).play(Mockito.anyString(), Mockito.<Map<String, String>> eq(argumentCaptorValue));
		assertTrue(sources.containsAll(argumentCaptorValue.keySet()));

		Collection<String> values = Arrays.asList(new String[] { "test", "test" });
		assertTrue(values.containsAll(argumentCaptorValue.values()));
	}

	@Test
	public void testPlayMapOfStringString() {
		doNothing().when(instance).play(Mockito.any(String.class), Mockito.anyMap());
		Map<String, String> params = new TreeMap<String, String>();
		params.put("1", "1");
		params.put("2", "2");

		instance.play(params);
		verify(instance).play(Mockito.eq("1,2"), Mockito.eq(params));
	}

	@Test
	public void testPlayStringMapOfStringString() {
		doNothing().when(instance).stopAndPlaySound(Mockito.any(MediaWrapper.class));
		doNothing().when(instance).createMediaWrapper(Mockito.anyString(), Mockito.anyMap());
		instance.wrappers = new HashMap<String, MediaWrapper<?>>();

		Map<String, String> params = new HashMap<String, String>();
		instance.play("test", params);

		verify(instance).createMediaWrapper(Mockito.eq("test"), Mockito.eq(params));

		instance.wrappers.put("test", mock(MediaWrapper.class));
		instance.play("test", null);
		verify(instance).stopAndPlaySound(Mockito.any(MediaWrapper.class));

	}

	@Test
	public void testAddHandlerForStopIfNotPresent() {
		// prepare
		createMediaWrapperMock();
		ArgumentCaptor<Type> arguments = ArgumentCaptor.forClass(Type.class);
		MediaEventHandler mediaEventHandler = mock(MediaEventHandler.class);
		doReturn(mediaEventHandler).when(instance).createOnStopMediaHandler(Mockito.eq(mediaWrapper));

		// test
		boolean isPresent = instance.addHandlerForStopIfNotPresent(mediaWrapper);
		assertFalse(isPresent);
		verifyAddHandlerForStopIfNotPresent(arguments, mediaEventHandler);
		isPresent = instance.addHandlerForStopIfNotPresent(mediaWrapper);
		assertTrue(isPresent);// no more interactions
		verifyAddHandlerForStopIfNotPresent(arguments, mediaEventHandler);
	}

	private void verifyAddHandlerForStopIfNotPresent(ArgumentCaptor<Type> arguments, MediaEventHandler mediaEventHandler) {
		verify(instance).createOnStopMediaHandler(Mockito.eq(mediaWrapper));
		verify(eventsBus).addHandlerToSource(arguments.capture(), Mockito.eq(mediaWrapper), Mockito.eq(mediaEventHandler));
		assertTrue(instance.onStopHandlers.containsKey(mediaWrapper));
		assertTrue(mediaEventHandler.equals(instance.onStopHandlers.get(mediaWrapper)));
	}

	@Test
	public void testCreateOnStopMediaHandler() {
		createMediaWrapperMock();
		doNothing().when(instance).firePlayEvent(Mockito.any(MediaWrapper.class));
		MediaEvent event = mock(MediaEvent.class);
		// test
		MediaEventHandler onStopMediaHandler = instance.createOnStopMediaHandler(mediaWrapper);
		onStopMediaHandler.onMediaEvent(event);
		verify(instance).firePlayEvent(Mockito.eq(mediaWrapper));
	}

	@Test
	public void testStopAndPlaySound() {
		createMediaWrapperMock();
		doReturn(false).when(instance).addHandlerForStopIfNotPresent(Mockito.eq(mediaWrapper));
		instance.stopAndPlaySound(mediaWrapper);
		verify(instance).addHandlerForStopIfNotPresent(Mockito.eq(mediaWrapper));
		verify(instance).stopAndPlaySound(Mockito.eq(mediaWrapper));
		verify(instance).fireStopEvent(Mockito.eq(mediaWrapper));
		Mockito.verifyNoMoreInteractions(instance);
	}

	@Test
	public void testGetWrappersSourcesKey() {
		List<String> sources = Arrays.asList("1", "2", "3");
		assertEquals("1,2,3", instance.getWrappersSourcesKey(sources));
	}

	@Test
	public void testGetMimeType() {
		String mp4 = "audio/mp4";
		String ogg = "audio/ogg";

		Map<String, String> map = new HashMap<String, String>();
		map.put("dummyurl.mp3", mp4);
		map.put("dummyurl.ogg", ogg);
		map.put("dummyurl.ogv", ogg);
		map.put("dummyurl.rar", "");

		// test
		for (Map.Entry<String, String> entry : map.entrySet()) {
			assertEquals(entry.getValue(), instance.getMimeType(entry.getKey()));
		}

	}

}
