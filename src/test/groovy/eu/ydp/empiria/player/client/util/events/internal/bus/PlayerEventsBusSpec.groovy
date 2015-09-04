package eu.ydp.empiria.player.client.util.events.internal.bus

import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope
import eu.ydp.gwtutil.client.scheduler.Scheduler
import spock.lang.Specification

import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.*

class PlayerEventsBusSpec extends Specification {

    def scheduler = Mock(Scheduler)
    def testObj = new PlayerEventsBus(scheduler)

    def event = new PlayerEvent(PAGE_CHANGE)
    def eventType = PlayerEvent.getType(PAGE_CHANGE)

    def event2 = new PlayerEvent(PAGE_LOADED)
    def eventTypes = PlayerEvent.getTypes(PAGE_CHANGE, PAGE_LOADED)

    def handler = Mock(PlayerEventHandler)
    def scopedHandler = Mock(PlayerEventHandler)
    def sourcedHandler = Mock(PlayerEventHandler)

    def asyncHandler = Mock(PlayerEventHandler)
    def asyncScopedHandler = Mock(PlayerEventHandler)
    def asyncSourcedHandler = Mock(PlayerEventHandler)

    def pageNumber = 4
    def scope = new CurrentPageScope(pageNumber)
    def badScope = new CurrentPageScope(14)

    def unimportantEvent = new PlayerEvent(PAGE_UNLOADED)
    def unimportantEventType = PlayerEvent.getType(PAGE_UNLOADED)
    def unimportantHandler = Mock(PlayerEventHandler)

    def source = new Object()
    def differentSource = new Object()

    def "should not dispatched when no handlers are registered"() {
        when:
        testObj.fireEvent(event)

        then:
        0 * _.onPlayerEvent(_)
    }

    def "should dispatch event to registered handler"() {
        testObj.addHandler(eventType, handler)

        when:
        testObj.fireEvent(event)

        then:
        1 * handler.onPlayerEvent(event)
        0 * _.onPlayerEvent()
    }

    def "should dispatch events only to events of the same type"() {
        testObj.addHandler(eventType, handler)
        testObj.addHandler(unimportantEventType, unimportantHandler)

        when:
        testObj.fireEvent(event)

        then:
        1 * handler.onPlayerEvent(event)
        0 * unimportantHandler.onPlayerEvent(event)
    }

    def "should not dispatch event on different event type"() {
        testObj.addHandler(eventType, handler)

        when:
        testObj.fireEvent(unimportantEvent)

        then:
        0 * handler.onPlayerEvent(event)
    }

    def "should dispatch multiple different events to single handler"() {
        testObj.addHandler(eventTypes, handler)

        when:
        testObj.fireEvent(event)

        then:
        1 * handler.onPlayerEvent(event)

        when:
        testObj.fireEvent(event2)

        then:
        1 * handler.onPlayerEvent(event2)
    }

    def "should dispatch event with scope to all handlers"() {
        testObj.addHandler(eventType, scopedHandler, scope)
        testObj.addHandler(eventType, handler)

        when:
        testObj.fireEvent(event, scope)

        then:
        1 * scopedHandler.onPlayerEvent(event)
        1 * handler.onPlayerEvent(event)
    }

    def "should dispatch event without scope to all handlers"() {
        testObj.addHandler(eventType, scopedHandler, scope)
        testObj.addHandler(eventType, handler)

        when:
        testObj.fireEvent(event)

        then:
        1 * scopedHandler.onPlayerEvent(event)
        1 * handler.onPlayerEvent(event)
    }

    def "should not dispatch event without different scope"() {
        testObj.addHandler(eventType, scopedHandler, scope)
        testObj.addHandler(eventType, handler)

        when:
        testObj.fireEvent(event, badScope)

        then:
        0 * scopedHandler.onPlayerEvent(event)
        1 * handler.onPlayerEvent(event)
    }

    def "should dispatch event to handlers with same source or without source"() {
        testObj.addHandlerToSource(eventType, source, sourcedHandler)
        testObj.addHandler(eventType, handler)

        when:
        testObj.fireEventFromSource(event, source)

        then:
        1 * handler.onPlayerEvent(event)
        1 * sourcedHandler.onPlayerEvent(event)
    }

    def "should not dispatch event to handlers with different source"() {
        testObj.addHandlerToSource(eventType, source, sourcedHandler)
        testObj.addHandler(eventType, handler)

        when:
        testObj.fireEventFromSource(event, differentSource)

        then:
        1 * handler.onPlayerEvent(event)
        0 * sourcedHandler.onPlayerEvent(event)
    }

    def "should dispatch event to handlers with same source and scope or without any of them"() {
        testObj.addHandlerToSource(eventType, source, sourcedHandler, scope)
        testObj.addHandler(eventType, handler, scope)

        when:
        testObj.fireEventFromSource(event, source, scope)

        then:
        1 * sourcedHandler.onPlayerEvent(event)
        1 * handler.onPlayerEvent(event)
    }

    def "should not dispatch event to handlers with same source and different scope"() {
        testObj.addHandlerToSource(eventType, source, sourcedHandler, scope)
        testObj.addHandler(eventType, handler, scope)

        when:
        testObj.fireEventFromSource(event, source, badScope)

        then:
        0 * sourcedHandler.onPlayerEvent(event)
        0 * handler.onPlayerEvent(event)
    }

    def "should not dispatch event to handlers with different source and same scope"() {
        testObj.addHandlerToSource(eventType, source, sourcedHandler, scope)
        testObj.addHandler(eventType, handler, scope)

        when:
        testObj.fireEventFromSource(event, differentSource, scope)

        then:
        0 * sourcedHandler.onPlayerEvent(event)
        1 * handler.onPlayerEvent(event)
    }

    def "should not dispatch event to handlers with different source and different scope"() {
        testObj.addHandlerToSource(eventType, source, sourcedHandler, scope)
        testObj.addHandler(eventType, handler, scope)

        when:
        testObj.fireEventFromSource(event, differentSource, badScope)

        then:
        0 * sourcedHandler.onPlayerEvent(event)
        0 * handler.onPlayerEvent(event)
    }

    def "should dispatch async events"() {
        testObj.addAsyncHandler(eventType, asyncHandler)

        when:
        testObj.fireAsyncEvent(event)

        then:
        1 * scheduler.scheduleDeferred(_) >> { args ->
            args[0].execute()
        }

        1 * asyncHandler.onPlayerEvent(event)
        0 * _.onPlayerEvent(_)
    }

    def "should notify handlers asynchronously on asynchronous event"() {
        testObj.addAsyncHandler(eventType, asyncHandler)
        testObj.addHandler(eventType, handler)

        when:
        testObj.fireAsyncEvent(event)

        then:
        2 * scheduler.scheduleDeferred(_) >> { args ->
            args[0].execute()
        }

        1 * asyncHandler.onPlayerEvent(event)
        1 * handler.onPlayerEvent(event)
        0 * _.onPlayerEvent(_)
    }

    def "should notify synchronous handlers synchronously and asynchronous handlers asynchronously on synchronous event"() {
        testObj.addAsyncHandler(eventType, asyncHandler)
        testObj.addHandler(eventType, handler)

        when:
        testObj.fireEvent(event)

        then:
        1 * scheduler.scheduleDeferred(_) >> { args ->
            args[0].execute()
        }

        1 * asyncHandler.onPlayerEvent(event)
        1 * handler.onPlayerEvent(event)
        0 * _.onPlayerEvent(_)
    }

    def "should dispatch async event with scope to all handlers"() {
        testObj.addAsyncHandler(eventType, asyncScopedHandler, scope)
        testObj.addAsyncHandler(eventType, asyncHandler)

        when:
        testObj.fireAsyncEvent(event, scope)

        then:
        2 * scheduler.scheduleDeferred(_) >> { args ->
            args[0].execute()
        }

        1 * asyncScopedHandler.onPlayerEvent(event)
        1 * asyncHandler.onPlayerEvent(event)
        0 * _.onPlayerEvent(_)
    }

    def "should dispatch async event without scope to all handlers"() {
        testObj.addAsyncHandler(eventType, asyncScopedHandler, scope)
        testObj.addAsyncHandler(eventType, asyncHandler)

        when:
        testObj.fireAsyncEvent(event)

        then:
        2 * scheduler.scheduleDeferred(_) >> { args ->
            args[0].execute()
        }

        1 * asyncScopedHandler.onPlayerEvent(event)
        1 * asyncHandler.onPlayerEvent(event)
        0 * _.onPlayerEvent(_)
    }

    def "should not dispatch async event without different scope"() {
        testObj.addAsyncHandler(eventType, asyncScopedHandler, scope)
        testObj.addAsyncHandler(eventType, asyncHandler)

        when:
        testObj.fireAsyncEvent(event, badScope)

        then:
        1 * scheduler.scheduleDeferred(_) >> { args ->
            args[0].execute()
        }

        0 * asyncScopedHandler.onPlayerEvent(event)
        1 * asyncHandler.onPlayerEvent(event)
        0 * _.onPlayerEvent(_)
    }

    def "should dispatch async event to handlers with same source or without source"() {
        testObj.addAsyncHandlerToSource(eventType, source, asyncSourcedHandler)
        testObj.addAsyncHandler(eventType, asyncHandler)

        when:
        testObj.fireAsyncEventFromSource(event, source)

        then:
        2 * scheduler.scheduleDeferred(_) >> { args ->
            args[0].execute()
        }

        1 * asyncSourcedHandler.onPlayerEvent(event)
        1 * asyncHandler.onPlayerEvent(event)
        0 * _.onPlayerEvent(_)
    }

    def "should not dispatch async event to handlers with different source"() {
        testObj.addAsyncHandlerToSource(eventType, source, asyncSourcedHandler)
        testObj.addAsyncHandler(eventType, asyncHandler)

        when:
        testObj.fireAsyncEventFromSource(event, differentSource)

        then:
        1 * scheduler.scheduleDeferred(_) >> { args ->
            args[0].execute()
        }

        1 * asyncHandler.onPlayerEvent(event)
        0 * asyncSourcedHandler.onPlayerEvent(event)
        0 * _.onPlayerEvent(_)
    }

    def "should dispatch async event to handlers with same source and scope or without any of them"() {
        testObj.addAsyncHandlerToSource(eventType, source, asyncSourcedHandler, scope)
        testObj.addAsyncHandler(eventType, asyncHandler, scope)

        when:
        testObj.fireAsyncEventFromSource(event, source, scope)

        then:
        2 * scheduler.scheduleDeferred(_) >> { args ->
            args[0].execute()
        }

        1 * asyncSourcedHandler.onPlayerEvent(event)
        1 * asyncHandler.onPlayerEvent(event)
        0 * _.onPlayerEvent(_)
    }

    def "should not dispatch async event to handlers with same source and different scope"() {
        testObj.addAsyncHandlerToSource(eventType, source, asyncSourcedHandler, scope)
        testObj.addAsyncHandler(eventType, asyncHandler, scope)

        when:
        testObj.fireAsyncEventFromSource(event, source, badScope)

        then:
        0 * scheduler.scheduleDeferred(_)
        0 * _.onPlayerEvent(_)
    }

    def "should not dispatch async event to handlers with different source and same scope"() {
        testObj.addAsyncHandlerToSource(eventType, source, asyncSourcedHandler, scope)
        testObj.addAsyncHandler(eventType, asyncHandler, scope)

        when:
        testObj.fireAsyncEventFromSource(event, differentSource, scope)

        then:
        1 * scheduler.scheduleDeferred(_) >> { args ->
            args[0].execute()
        }

        0 * asyncSourcedHandler.onPlayerEvent(event)
        1 * asyncHandler.onPlayerEvent(event)
        0 * _.onPlayerEvent(_)
    }

    def "should not dispatch async event to handlers with different source and different scope"() {
        testObj.addAsyncHandlerToSource(eventType, source, asyncSourcedHandler, scope)
        testObj.addAsyncHandler(eventType, asyncHandler, scope)

        when:
        testObj.fireAsyncEventFromSource(event, differentSource, badScope)

        then:
        0 * scheduler.scheduleDeferred(_)
        0 * _.onPlayerEvent(_)
    }

    def "should unregister scoped handler when page is removed"() {
        def pageRemovedEvent = new PlayerEvent(PAGE_REMOVED, pageNumber, null)
        testObj.addHandler(eventType, handler, scope)

        when:
        testObj.fireEvent(event, scope)

        then:
        1 * handler.onPlayerEvent(event)

        when:
        testObj.onPlayerEvent(pageRemovedEvent)
        testObj.fireEvent(event, scope)

        then:
        0 * handler.onPlayerEvent(event)
    }
}
