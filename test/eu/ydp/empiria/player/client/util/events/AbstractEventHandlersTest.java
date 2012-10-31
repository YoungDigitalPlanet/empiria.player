package eu.ydp.empiria.player.client.util.events;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

@SuppressWarnings("PMD")
public class AbstractEventHandlersTest {
	class AbstractEventHandlersImpl extends AbstractEventHandlers<MediaEventHandler, MediaEventTypes, MediaEvent> {
		@Override
		protected void dispatchEvent(MediaEventHandler handler, MediaEvent event) {
			handler.onMediaEvent(event);
		}
	}

	private AbstractEventHandlers<MediaEventHandler, MediaEventTypes, MediaEvent> getAbstractEventHandlers() {
		return new AbstractEventHandlers<MediaEventHandler, MediaEventTypes, MediaEvent>() {
			@Override
			protected void dispatchEvent(MediaEventHandler handler, MediaEvent event) {
				handler.onMediaEvent(event);
			}
		};
	}

	private AbstractEventHandlers<PlayerEventHandler, PlayerEventTypes, PlayerEvent> getPlayerAbstractEventHandlers() {
		return new AbstractEventHandlers<PlayerEventHandler, PlayerEventTypes, PlayerEvent>() {
			@Override
			protected void dispatchEvent(PlayerEventHandler handler, PlayerEvent event) {
				handler.onPlayerEvent(event);
			}
		};
	}

	@Test
	public void getHandlersTest() {
		AbstractEventHandlers<MediaEventHandler, MediaEventTypes, MediaEvent> abstractEventHandlers = getAbstractEventHandlers();
		MediaEventHandler handler = mock(MediaEventHandler.class);
		MediaEventHandler handler2 = mock(MediaEventHandler.class);

		abstractEventHandlers.addHandler(handler, MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE));
		abstractEventHandlers.addHandler(handler, MediaEvent.getType(MediaEventTypes.ON_END));
		abstractEventHandlers.addHandler(handler2, MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE));
		abstractEventHandlers.addHandler(handler2, MediaEvent.getType(MediaEventTypes.ON_END));

		// test
		Set<MediaEventHandler> handlers = abstractEventHandlers.getHandlers(MediaEvent.getType(MediaEventTypes.ON_END));
		assertTrue(handlers.contains(handler));
		assertTrue(handlers.contains(handler2));
		handlers = abstractEventHandlers.getHandlers(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE));
		assertTrue(handlers.contains(handler));
		assertTrue(handlers.contains(handler2));
	}

	@Test
	public void removeHandlerTest() {
		AbstractEventHandlers<MediaEventHandler, MediaEventTypes, MediaEvent> abstractEventHandlers = getAbstractEventHandlers();
		MediaEventHandler handler = mock(MediaEventHandler.class);
		MediaEventHandler handler2 = mock(MediaEventHandler.class);

		HandlerRegistration handlerRegistration = abstractEventHandlers.addHandler(handler, MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE));
		abstractEventHandlers.addHandler(handler, MediaEvent.getType(MediaEventTypes.ON_END));
		abstractEventHandlers.addHandler(handler2, MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE));
		HandlerRegistration handlerRegistration2 = abstractEventHandlers.addHandler(handler2, MediaEvent.getType(MediaEventTypes.ON_END));
		handlerRegistration.removeHandler();
		handlerRegistration2.removeHandler();

		// test
		Set<MediaEventHandler> handlers = abstractEventHandlers.getHandlers(MediaEvent.getType(MediaEventTypes.ON_END));
		assertTrue(handlers.contains(handler));
		assertFalse(handlers.contains(handler2));
		handlers = abstractEventHandlers.getHandlers(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE));
		assertFalse(handlers.contains(handler));
		assertTrue(handlers.contains(handler2));
	}

	@Test
	public void fireEventTest() {
		AbstractEventHandlers<MediaEventHandler, MediaEventTypes, MediaEvent> abstractEventHandlers = getAbstractEventHandlers();
		MediaEventHandler handler = mock(MediaEventHandler.class);
		MediaEventHandler handler2 = mock(MediaEventHandler.class);

		abstractEventHandlers.addHandler(handler, MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE));
		abstractEventHandlers.addHandler(handler, MediaEvent.getType(MediaEventTypes.ON_END));
		abstractEventHandlers.addHandler(handler2, MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE));
		abstractEventHandlers.addHandler(handler2, MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_OPEN));

		// test
		MediaEvent mediaEvent = new MediaEvent(MediaEventTypes.ON_DURATION_CHANGE);
		abstractEventHandlers.fireEvent(mediaEvent);
		verify(handler).onMediaEvent(mediaEvent);
		verify(handler2).onMediaEvent(mediaEvent);
		Mockito.reset(handler2);

		mediaEvent = new MediaEvent(MediaEventTypes.ON_FULL_SCREEN_OPEN);
		abstractEventHandlers.fireEvent(mediaEvent);
		verify(handler2).onMediaEvent(mediaEvent);
		verify(handler2).onMediaEvent(Mockito.any(MediaEvent.class));
	}

	@Test
	public void addMultipleEventHandlerTest() {
		AbstractEventHandlers<PlayerEventHandler, PlayerEventTypes, PlayerEvent> abstractEventHandlers = getPlayerAbstractEventHandlers();
		PlayerEventHandler handler = mock(PlayerEventHandler.class);
		PlayerEventHandler handler2 = mock(PlayerEventHandler.class);

		abstractEventHandlers.addHandlers(handler, PlayerEvent.getTypes(PlayerEventTypes.LOAD_PAGE_VIEW, PlayerEventTypes.AFTER_FLOW));
		abstractEventHandlers.addHandler(handler2, PlayerEvent.getType(PlayerEventTypes.LOAD_PAGE_VIEW));

		// test
		PlayerEvent event = new PlayerEvent(PlayerEventTypes.LOAD_PAGE_VIEW);
		abstractEventHandlers.fireEvent(event);
		verify(handler).onPlayerEvent(event);
		verify(handler2).onPlayerEvent(event);
		Mockito.reset(handler2);
		Mockito.reset(handler);
		event = new PlayerEvent(PlayerEventTypes.AFTER_FLOW);
		abstractEventHandlers.fireEvent(event);
		verify(handler).onPlayerEvent(event);
		verify(handler).onPlayerEvent(Mockito.any(PlayerEvent.class));
		verify(handler2,times(0)).onPlayerEvent(event);
	}

}
