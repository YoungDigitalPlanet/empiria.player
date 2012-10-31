package eu.ydp.empiria.player.client.util.events.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.Event;
import eu.ydp.empiria.player.client.util.events.Event.Type;
import eu.ydp.empiria.player.client.util.events.EventHandler;
import eu.ydp.empiria.player.client.util.events.command.FireCommand;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;
import eu.ydp.empiria.player.client.util.events.scope.PageScope;
import eu.ydp.empiria.player.client.util.scheduler.Scheduler;

public class PlayerEventsBus implements EventsBus, PlayerEventHandler {
	private final Map<Event.Type<?, ?>, Map<Object, Map<EventScope<?>, List<?>>>> syncMap = new HashMap<Event.Type<?, ?>, Map<Object, Map<EventScope<?>, List<?>>>>();
	private final Map<Event.Type<?, ?>, Map<Object, Map<EventScope<?>, List<?>>>> asyncMap = new HashMap<Event.Type<?, ?>, Map<Object, Map<EventScope<?>, List<?>>>>();

	@Inject
	private Scheduler scheduler;

	public PlayerEventsBus() {
		// Czyszczenie szyny z niepotrzebnych handlerow
		this.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_REMOVED), this);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>> HandlerRegistration addHandler(Type<H, T> type, H handler) {
		return doAdd(type, null, handler, false, null);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>> HandlerRegistration[] addHandler(Type<H, T>[] types, H handler) {
		return doAddAll(types, null, handler, false, null);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>> HandlerRegistration addHandler(Type<H, T> type, H handler, EventScope<?> eventScope) {
		return doAdd(type, null, handler, false, eventScope);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>> HandlerRegistration[] addHandler(Type<H, T>[] types, H handler, EventScope<?> eventScope) {
		return doAddAll(types, null, handler, false, eventScope);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>> HandlerRegistration addHandlerToSource(Type<H, T> type, Object source, H handler) {
		return doAdd(type, source, handler, false, null);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>> HandlerRegistration addHandlerToSource(Type<H, T> type, Object source, H handler, EventScope<?> eventScope) {
		return doAdd(type, source, handler, false, eventScope);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>> HandlerRegistration addAsyncHandler(Type<H, T> type, H handler) {
		return doAdd(type, null, handler, true, null);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>> HandlerRegistration addAsyncHandler(Type<H, T> type, H handler, EventScope<?> eventScope) {
		return doAdd(type, null, handler, true, eventScope);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>> HandlerRegistration addAsyncHandlerToSource(Type<H, T> type, Object source, H handler) {
		return doAdd(type, source, handler, true, null);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>> HandlerRegistration addAsyncHandlerToSource(Type<H, T> type, Object source, H handler, EventScope<?> eventScope) {
		return doAdd(type, source, handler, true, eventScope);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEvent(E event) {
		fireEvent(event, null, false, null);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEvent(E event, EventScope<?> eventScope) {
		fireEvent(event, null, false, eventScope);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventWithCallback(E event) {
		fireEvent(event, null, false, null);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventWithCallback(E event, EventScope<?> eventScope) {
		fireEvent(event, null, false, eventScope);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventFromSource(E event, Object source) {
		fireEvent(event, source, false, null);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventFromSource(E event, Object source, EventScope<?> eventScope) {
		fireEvent(event, source, false, eventScope);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireAsyncEvent(E event) {
		fireEvent(event, null, true, null);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireAsyncEvent(E event, EventScope<?> eventScope) {
		fireEvent(event, null, true, eventScope);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireAsyncEventFromSource(E event, Object source) {
		fireEvent(event, source, true, null);
	}

	@Override
	public <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireAsyncEventFromSource(E event, Object source, EventScope<?> eventScope) {
		fireEvent(event, source, true, eventScope);
	}

	@SuppressWarnings({ "unchecked", "PMD" })
	private <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventAsync(E event, Object source, Map<Object, Map<EventScope<?>, List<?>>> map,
			EventScope<?> eventScope) {
		if (map != null) {
			Map<EventScope<?>, List<?>> handler = null;
			if ((handler = map.get(source)) != null) {
				for (Map.Entry<EventScope<?>, List<?>> entry : handler.entrySet()) {
					if (eventScope == null || entry.getKey() == null || eventScope.equals(entry.getKey())) {
						//some problems with ConcurrentModificationException
						List<?> handlers = GWT.isProdMode() ? entry.getValue() : new ArrayList<Object>(entry.getValue());
						for (Object e : handlers) {
							scheduler.scheduleDeferred(new FireCommand<H, Event<H, T>>((H) e, event));
						}
					}
				}
			}
			// handlery bez okreslonego obiektu source
			if (source != null && (handler = map.get(null)) != null) {
				for (Map.Entry<EventScope<?>, List<?>> entry : handler.entrySet()) {
					if (eventScope == null || entry.getKey() == null || eventScope.equals(entry.getKey())) {
						//some problems with ConcurrentModificationException
						List<?> handlers = GWT.isProdMode() ? entry.getValue() : new ArrayList<Object>(entry.getValue());
						for (Object e : handlers) {
							scheduler.scheduleDeferred(new FireCommand<H, Event<H, T>>((H) e, event));
						}
					}
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "PMD" })
	private <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEvent(E event, Object source, Map<Object, Map<EventScope<?>, List<?>>> map,
			EventScope<?> eventScope) {
		if (map != null) {
			Map<EventScope<?>, List<?>> handler = null;
			if ((handler = map.get(source)) != null) {
				for (Map.Entry<EventScope<?>, List<?>> entry : handler.entrySet()) {
					if (eventScope == null || entry.getKey() == null || eventScope.equals(entry.getKey())) {
						//some problems with ConcurrentModificationException
						List<?> handlers = GWT.isProdMode() ? entry.getValue() : new ArrayList<Object>(entry.getValue());
						for (Object e : handlers) {
							event.dispatch((H) e);
						}
					}
				}
			}
			// handlery bez okreslonego obiektu source
			if (source != null && (handler = map.get(null)) != null) {
				for (Map.Entry<EventScope<?>, List<?>> entry : handler.entrySet()) {
					if (eventScope == null || entry.getKey() == null || eventScope.equals(entry.getKey())) {
						//some problems with ConcurrentModificationException
						List<?> handlers = GWT.isProdMode() ? entry.getValue() : new ArrayList<Object>(entry.getValue());
						for (Object e : handlers) {
							event.dispatch((H) e);
						}
					}
				}
			}
		}
	}

	private <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEvent(E event, Object source, boolean async, EventScope<?> eventScope) {
		if (async) {
			fireEventAsync(event, source, getHandlersList(event, asyncMap), eventScope);
			fireEventAsync(event, source, getHandlersList(event, syncMap), eventScope);
		} else {
			fireEventAsync(event, source, getHandlersList(event, asyncMap), eventScope);
			fireEvent(event, source, getHandlersList(event, syncMap), eventScope);
		}
	}

	private <H extends EventHandler, T extends Enum<T>> Map<Object, Map<EventScope<?>, List<?>>> getHandlersList(Event<H, T> type,
			Map<Event.Type<?, ?>, Map<Object, Map<EventScope<?>, List<?>>>> map) {
		return map.get(type.getAssociatedType());
	}

	private <H extends EventHandler, T extends Enum<T>> HandlerRegistration[] doAddAll(final Type<H, T> types[], final Object source, final H handler, final boolean async,
			final EventScope<?> eventScope) {
		HandlerRegistration[] registrations = new HandlerRegistration[types.length];
		for (int x = 0; x < types.length; ++x) {
			registrations[x] = doAdd(types[x], source, handler, async, eventScope);
		}
		return registrations;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "PMD" })
	private <H extends EventHandler, T extends Enum<T>> HandlerRegistration doAdd(final Type<H, T> type, final Object source, final H handler, final boolean async,
			final EventScope<?> eventScope) {
		if (type == null) {
			throw new NullPointerException("Cannot add a handler with a null type");
		}
		if (handler == null) {
			throw new NullPointerException("Cannot add a null handler");
		}

		Map<Object, Map<EventScope<?>, List<?>>> handlerMap = async ? asyncMap.get(type) : syncMap.get(type);
		if (handlerMap == null) {
			handlerMap = new HashMap<Object, Map<EventScope<?>, List<?>>>();
			if (async) {
				asyncMap.put(type, handlerMap);
			} else {
				syncMap.put(type, handlerMap);
			}
		}
		Map<EventScope<?>, List<?>> handlersScope = handlerMap.get(source);
		if (handlersScope == null) {
			handlersScope = new HashMap<EventScope<?>, List<?>>();
			handlerMap.put(source, handlersScope);
			handlersScope.put(eventScope, new ArrayList());
		}
		List handlers = handlersScope.get(eventScope);
		if (handlers == null) {
			handlers = new ArrayList();
			handlersScope.put(eventScope, handlers);
		}
		handlers.add(handler);

		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				doRemove(type, source, handler, eventScope, async);
			}
		};

	}

	private <H extends EventHandler, T extends Enum<T>> void doRemove(Type<H, T> type, Object source, H handler, EventScope<?> scope, boolean async) {
		Map<Object, Map<EventScope<?>, List<?>>> handlerMap = async ? asyncMap.get(type) : syncMap.get(type);
		if (handlerMap != null) {
			Map<EventScope<?>, List<?>> handlers = handlerMap.get(source);
			if (handlers != null) {
				handlers.get(scope).remove(handler);
			}
		}
	}

	private void doRemoveAllWithScope(EventScope<?> scope) {
		for (Map<Event.Type<?, ?>, Map<Object, Map<EventScope<?>, List<?>>>> map : Arrays.asList(syncMap, asyncMap)) {
			for (Map<Object, Map<EventScope<?>, List<?>>> handlerMap : map.values()) {
				for (Map.Entry<Object, Map<EventScope<?>, List<?>>> entry : handlerMap.entrySet()) {
					Map<EventScope<?>, List<?>> handlers = entry.getValue();
					if (handlers == null) {
						continue;
					}
					handlers.remove(scope);
				}
			}
		}
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		if (event.getType() == PlayerEventTypes.PAGE_REMOVED) {
			int pageNumber = (Integer) event.getValue();
			EventScope<?> eventScope = new PageScope(pageNumber);
			doRemoveAllWithScope(eventScope);
		}
	}
}
