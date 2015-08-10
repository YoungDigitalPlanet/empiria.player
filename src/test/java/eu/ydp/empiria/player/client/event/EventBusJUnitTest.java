package eu.ydp.empiria.player.client.event;

import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

/**
 * EventBus Tests
 */
@SuppressWarnings("PMD")
public class EventBusJUnitTest extends AbstractTestBase {

    EventsBus eventsBus;
    PlayerEventHandler eventHandler, scopeEventHandler;
    PlayerEvent playerEvent;

    private void prepare() {
        eventsBus = injector.getInstance(EventsBus.class);
        eventHandler = mock(PlayerEventHandler.class);
        scopeEventHandler = mock(PlayerEventHandler.class);
        playerEvent = new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER);
    }

    @Test
    public void addMultipleHandlersWithoutScope() {
        prepare();
        playerEvent = new PlayerEvent(PlayerEventTypes.PAGE_REMOVED, 0, null);
        eventsBus.addHandler(PlayerEvent.getTypes(PlayerEventTypes.PAGE_REMOVED, PlayerEventTypes.PAGE_CHANGING), eventHandler);
        eventsBus.fireEvent(playerEvent, new CurrentPageScope(0));
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.reset(eventHandler);
        playerEvent = new PlayerEvent(PlayerEventTypes.PAGE_CHANGING);
        eventsBus.fireEvent(playerEvent, new CurrentPageScope(0));
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
    }

    private void prepareHandlers() {
        prepare();
        // handler globalny
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), eventHandler);
        // handler na scope
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), scopeEventHandler, new CurrentPageScope(0));

    }

    private void prepareAsyncHandlers() {
        prepare();
        // handler globalny
        eventsBus.addAsyncHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), eventHandler);
        // handler na scope
        eventsBus.addAsyncHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), scopeEventHandler, new CurrentPageScope(0));
    }

    @Test
    public void eventWithoutScope() {
        prepareHandlers();
        eventsBus.fireEvent(playerEvent);
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);

    }

    @Test
    public void eventWithoutScopeWithSource() {
        prepareHandlers();
        eventsBus.fireEventFromSource(playerEvent, "source");
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);

    }

    @Test
    public void eventWithScope() {
        prepareHandlers();
        eventsBus.fireEvent(playerEvent, new CurrentPageScope(0));
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        prepareHandlers();
        eventsBus.fireEvent(playerEvent, new CurrentPageScope(1));
        Mockito.verify(eventHandler).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(0)).onPlayerEvent(playerEvent);
    }

    @Test
    public void eventWithScopeWithSource() {
        prepareHandlers();
        eventsBus.fireEventFromSource(playerEvent, "source", new CurrentPageScope(0));
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        prepareHandlers();
        eventsBus.fireEventFromSource(playerEvent, "source", new CurrentPageScope(1));
        Mockito.verify(eventHandler).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(0)).onPlayerEvent(playerEvent);
    }

    @Test
    public void removeHandler() {
        prepare();
        // handler globalny
        HandlerRegistration asyncRegistration = eventsBus.addAsyncHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), eventHandler);
        // handler na scope
        HandlerRegistration registration = eventsBus
                .addHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), scopeEventHandler, new CurrentPageScope(0));
        eventsBus.fireEvent(playerEvent);
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        asyncRegistration.removeHandler();
        registration.removeHandler();
        eventsBus.fireEvent(playerEvent);
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
    }

    @Test
    public void clearEventsForScope() {
        prepareHandlers();
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_REMOVED, 0, null));
        eventsBus.fireEvent(playerEvent, new CurrentPageScope(0));
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(0)).onPlayerEvent(playerEvent);
        prepareAsyncHandlers();
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_REMOVED, 0, null));
        eventsBus.fireEvent(playerEvent, new CurrentPageScope(0));
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(0)).onPlayerEvent(playerEvent);
    }

    @Test
    public void asyncEventWithoutScope() {
        prepareHandlers();
        eventsBus.fireAsyncEvent(playerEvent);
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);

    }

    @Test
    public void asyncEventWithScope() {
        prepareHandlers();
        eventsBus.fireAsyncEvent(playerEvent, new CurrentPageScope(0));
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        prepareHandlers();
        eventsBus.fireAsyncEvent(playerEvent, new CurrentPageScope(1));
        Mockito.verify(eventHandler).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(0)).onPlayerEvent(playerEvent);
    }

    @Test
    public void asyncEventWithoutScopeWithSource() {
        prepareHandlers();
        eventsBus.fireAsyncEventFromSource(playerEvent, "source");
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);

    }

    @Test
    public void asyncEventWithScopeWithSource() {
        prepareHandlers();
        eventsBus.fireAsyncEventFromSource(playerEvent, "source", new CurrentPageScope(0));
        Mockito.verify(eventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(1)).onPlayerEvent(playerEvent);
        prepareHandlers();
        eventsBus.fireAsyncEventFromSource(playerEvent, "source", new CurrentPageScope(1));
        Mockito.verify(eventHandler).onPlayerEvent(playerEvent);
        Mockito.verify(scopeEventHandler, Mockito.times(0)).onPlayerEvent(playerEvent);
    }

    @Test(expected = NullPointerException.class)
    public void addNullHandlerAndThrowNullPointerException() {
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), null);
    }

}
