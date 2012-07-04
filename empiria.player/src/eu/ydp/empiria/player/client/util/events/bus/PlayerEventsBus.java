package eu.ydp.empiria.player.client.util.events.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.Event;
import eu.ydp.empiria.player.client.util.events.Event.Type;
import eu.ydp.empiria.player.client.util.events.EventHandler;
import eu.ydp.empiria.player.client.util.events.command.FireCommand;

public class PlayerEventsBus implements EventsBus{
	private final Map<Event.Type<?,?>, Map<Object, List<?>>> syncMap = new HashMap<Event.Type<?,?>, Map<Object, List<?>>>();
	private final Map<Event.Type<?,?>, Map<Object, List<?>>> asyncMap = new HashMap<Event.Type<?,?>, Map<Object, List<?>>>();
	private Scheduler scheduler = Scheduler.get();


	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.util.events.bus.EventsBus#addHandler(eu.ydp.empiria.player.client.util.events.Event.Type, H)
	 */
	@Override
	public <H extends EventHandler,T extends Enum<T>> HandlerRegistration addHandler(Type<H,T> type, H handler) {
		return doAdd(type, null, handler, false);
	}


	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.util.events.bus.EventsBus#addHandlerToSource(eu.ydp.empiria.player.client.util.events.Event.Type, java.lang.Object, H)
	 */
	@Override
	public <H extends EventHandler,T extends Enum<T>> HandlerRegistration addHandlerToSource(Type<H,T> type, Object source, H handler) {
		return doAdd(type, source, handler, false);
	}


	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.util.events.bus.EventsBus#addAsyncHandler(eu.ydp.empiria.player.client.util.events.Event.Type, H)
	 */
	@Override
	public <H extends EventHandler,T extends Enum<T>> HandlerRegistration addAsyncHandler(Type<H,T> type, H handler) {
		return doAdd(type, null, handler, true);
	}


	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.util.events.bus.EventsBus#addAsyncHandlerToSource(eu.ydp.empiria.player.client.util.events.Event.Type, java.lang.Object, H)
	 */
	@Override
	public <H extends EventHandler,T extends Enum<T>> HandlerRegistration addAsyncHandlerToSource(Type<H,T> type, Object source, H handler) {
		return doAdd(type, source, handler, true);
	}

	private <H extends EventHandler,T extends Enum<T>> void doRemove(Type<H,T> type, Object source, H handler, boolean async) {
		// TODO sprawdzic
		Map<Object, List<?>> handlerMap = async ? asyncMap.get(type) : syncMap.get(type);
		if (handlerMap != null) {
			List<?> handlers = handlerMap.get(source);
			if (handlers != null) {
				handlers.remove(handler);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <H extends EventHandler,T extends Enum<T>> HandlerRegistration doAdd(final Type<H,T> type, final Object source, final H handler, final boolean async) {
		if (type == null) {
			throw new NullPointerException("Cannot add a handler with a null type");
		}
		if (handler == null) {
			throw new NullPointerException("Cannot add a null handler");
		}

		Map<Object, List<?>> handlerMap = async ? asyncMap.get(type) : syncMap.get(type);
		if (handlerMap == null) {
			handlerMap = new HashMap<Object, List<?>>();
			if (async) {
				asyncMap.put(type, handlerMap);
			} else {
				syncMap.put(type, handlerMap);
			}
		}
		List handlers = handlerMap.get(source);
		if (handlers == null) {
			handlers = new ArrayList();
			handlerMap.put(source, handlers);
		}
		handlers.add(handler);

		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				doRemove(type, source, handler, async);
			}
		};

	}


	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.util.events.bus.EventsBus#fireEvent(E)
	 */
	@Override
	public <H extends EventHandler,T extends Enum<T>, E extends Event<H,T>> void fireEvent(E event) {
		fireEvent(event, null, false);
	}


	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.util.events.bus.EventsBus#fireEventWithCallback(E)
	 */
	@Override
	public <H extends EventHandler,T extends Enum<T>, E extends Event<H,T>> void fireEventWithCallback(E event) {
		fireEvent(event, null, false);
	}


	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.util.events.bus.EventsBus#fireEventFromSource(E, java.lang.Object)
	 */
	@Override
	public <H extends EventHandler,T extends Enum<T>, E extends Event<H,T>> void fireEventFromSource(E event, Object source) {
		fireEvent(event, source, false);
	}


	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.util.events.bus.EventsBus#fireAsyncEvent(E)
	 */
	@Override
	public <H extends EventHandler,T extends Enum<T>, E extends Event<H,T>> void fireAsyncEvent(E event) {
		fireEvent(event, null, true);
	}


	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.util.events.bus.EventsBus#fireAsyncEventFromSource(E, java.lang.Object)
	 */
	@Override
	public <H extends EventHandler,T extends Enum<T>, E extends Event<H,T>> void fireAsyncEventFromSource(E event, Object source) {
		fireEvent(event, source, true);
	}

	@SuppressWarnings("unchecked")
	private <H extends EventHandler,T extends Enum<T>, E extends Event<H,T>> void fireEventAsync(E event, Object source, Map<Object, List<?>> map) {
		if (map != null) {
			List<H> handler = (List<H>) map.get(source);
			if (handler != null && source != null) {
				for (H e : handler) {
					scheduler.scheduleDeferred(new FireCommand<H, Event<H,T>>(e, event));
				}
			}
			// handlery bez okreslonego obiektu source
			handler = (List<H>) map.get(null);
			if (handler != null) {
				for (H e : handler) {
					scheduler.scheduleDeferred(new FireCommand<H, Event<H,T>>(e, event));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <H extends EventHandler,T extends Enum<T>, E extends Event<H,T>> void fireEvent(E event, Object source, Map<Object, List<?>> map) {
		if (map != null) {
			List<H> handler = (List<H>) map.get(source);
			if (handler != null && source != null) {
				for (H e : handler) {
					event.dispatch(e);
				}
			}
			// handlery bez okreslonego obiektu source
			handler = (List<H>) map.get(null);
			if (handler != null) {
				for (H e : handler) {
					event.dispatch(e);
				}
			}
		}
	}

	private <H extends EventHandler,T extends Enum<T>, E extends Event<H,T>> void fireEvent(E event, Object source, boolean async) {
		if (async) {
			fireEventAsync(event, source, getHandlersList(event, asyncMap));
			fireEventAsync(event, source, getHandlersList(event, syncMap));
		} else {
			fireEventAsync(event, source, getHandlersList(event, asyncMap));
			fireEvent(event, source, getHandlersList(event, syncMap));
		}
	}

	private <H extends EventHandler,T extends Enum<T>> Map<Object, List<?>> getHandlersList(Event<H,T> type, Map<Event.Type<?,?>, Map<Object, List<?>>> map) {
		return map.get(type.getAssociatedType());
	}

}
