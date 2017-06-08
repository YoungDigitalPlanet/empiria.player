/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.util.events.internal;

import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.event.HandlerRegistration;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AbstractEventHandlersTest {
    abstract class AbstractEventHandlerImpl<H extends EventHandler, E extends Enum<E>, EV extends Event<H, E>> extends AbstractEventHandler<H, E, EV> {
        @Override
        public HandlerRegistration addHandler(H handler, EventType<H, E> event) {
            return super.addHandler(handler, event);
        }

        @Override
        public HandlerRegistration[] addHandlers(H handler, EventType<H, E>[] event) {
            return super.addHandlers(handler, event);
        }

        @Override
        public Set<H> getHandlers(EventType<H, E> event) {
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

    class MediaEventHandlerImpl extends AbstractEventHandlerImpl<MediaEventHandler, MediaEventTypes, MediaEvent> {
        @Override
        protected void dispatchEvent(MediaEventHandler handler, MediaEvent event) {
            handler.onMediaEvent(event);
        }
    }

    private MediaEventHandlerImpl getMediaEventHandler() {
        return new MediaEventHandlerImpl();
    }

    private AbstractPlayerEventHandlerImpl getPlayerEventHandler() {
        return new AbstractPlayerEventHandlerImpl();
    }

    @Test
    public void getHandlersTest() {
        MediaEventHandlerImpl abstractEventHandlers = getMediaEventHandler();
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
        MediaEventHandlerImpl abstractEventHandlers = getMediaEventHandler();
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
        MediaEventHandlerImpl abstractEventHandlers = getMediaEventHandler();
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
        verify(handler2).onMediaEvent(Matchers.any(MediaEvent.class));
    }

    @Test
    public void addMultipleEventHandlerTest() {
        AbstractPlayerEventHandlerImpl abstractEventHandlers = getPlayerEventHandler();
        PlayerEventHandler handler = mock(PlayerEventHandler.class);

        abstractEventHandlers.addHandler(handler, PlayerEvent.getType(PlayerEventTypes.LOAD_PAGE_VIEW));

        // test
        PlayerEvent event = new PlayerEvent(PlayerEventTypes.LOAD_PAGE_VIEW);
        abstractEventHandlers.fireEvent(event);
        verify(handler).onPlayerEvent(event);
        Mockito.reset(handler);
    }

}
