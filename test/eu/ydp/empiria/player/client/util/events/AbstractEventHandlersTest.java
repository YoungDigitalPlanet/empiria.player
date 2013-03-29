package eu.ydp.empiria.player.client.util.events;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.event.AbstractEventHandler;
import eu.ydp.gwtutil.client.event.Event;
import eu.ydp.gwtutil.client.event.EventHandler;
import eu.ydp.gwtutil.client.event.EventImpl.Type;
import eu.ydp.gwtutil.client.event.HandlerRegistration;

@SuppressWarnings("PMD")
public class AbstractEventHandlersTest {
	abstract class AbstractEventHandlerImpl<H extends EventHandler, E extends Enum<E>, EV extends Event<H, E>> extends AbstractEventHandler<H, E, EV>{
		@Override
		public HandlerRegistration addHandler(H handler, Type<H, E> event) {
			return super.addHandler(handler, event);
		}

		@Override
		public HandlerRegistration[] addHandlers(H handler, Type<H, E>[] event) {
			return super.addHandlers(handler, event);
		}

		@Override
		public Set<H> getHandlers(Type<H, E> event) {
			return super.getHandlers(event);
		}

		@Override
		public void fireEvent(EV event) {
			super.fireEvent(event);
		}
	}

	class AbstractPlayerEventHandlerImpl extends AbstractEventHandlerImpl<PlayerEventHandler, PlayerEventTypes, PlayerEvent> {
		@Override
		protected void dispatchEvent(PlayerEventHandler handler, PlayerEvent event) {
			handler.onPlayerEvent(event);
		}
	}

	class AbstractMediaEventHandlerImpl extends AbstractEventHandlerImpl<MediaEventHandler, MediaEventTypes, MediaEvent> {
		@Override
		protected void dispatchEvent(MediaEventHandler handler, MediaEvent event) {
			handler.onMediaEvent(event);
		}
	}


	private AbstractMediaEventHandlerImpl getMediaEventHandler() {
		return new AbstractMediaEventHandlerImpl();
	}

	private AbstractPlayerEventHandlerImpl getPlayerEventHandler() {
		return new AbstractPlayerEventHandlerImpl();
	}

	@Test
	public void getHandlersTest() {
		AbstractMediaEventHandlerImpl abstractEventHandlers = getMediaEventHandler();
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
		AbstractMediaEventHandlerImpl abstractEventHandlers = getMediaEventHandler();
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
		AbstractMediaEventHandlerImpl abstractEventHandlers = getMediaEventHandler();
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
		AbstractPlayerEventHandlerImpl abstractEventHandlers = getPlayerEventHandler();
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
