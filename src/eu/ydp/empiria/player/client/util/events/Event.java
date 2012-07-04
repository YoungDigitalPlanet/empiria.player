package eu.ydp.empiria.player.client.util.events;

import eu.ydp.empiria.player.client.util.events.bus.PlayerEventsBus;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;

/**
 * Base Event object.
 *
 * @param <H>
 *            interface implemented by handlers of this kind of event
 */
public abstract class Event<H,T extends Enum<T>> {
	/**
	 * Type class used to register events with an {@link PlayerEventsBus}.
	 *
	 * @param <H>
	 *            handler type
	 */
	public static class Type<H,T extends Enum<T>> {
		private static int nextHashCode;
		private final int index;
		private Enum<T> type;
		private EventScope<?> eventScope = null;

		/**
		 * Constructor.
		 */
		public Type(Enum<T> type, EventScope<?> eventScope) {
			nextHashCode += type.ordinal();
			index = ++nextHashCode;
			this.type = type;
			this.eventScope = eventScope;
		}

		public Enum<T> getType() {
			return type;
		}

		public EventScope<?> getEventScope() {
			return eventScope;
		}

		@Override
		public int hashCode() {
			return index;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Type [index=");
			builder.append(index);
			builder.append(", type=");
			builder.append(type);
			builder.append(", eventScope=");
			builder.append(eventScope);
			builder.append("]");
			return builder.toString();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Type<?,?> other = (Type<?,?>) obj;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}
	}

	private Object source;

	/**
	 * Constructor.
	 */
	protected Event() {
	}

	/**
	 * Returns the {@link Type} used to register this event, allowing an
	 * {@link PlayerEventsBus} to find handlers of the appropriate class.
	 *
	 * @return the type
	 */
	public abstract Type<H,T> getAssociatedType();

	/**
	 * Returns the source for this event. The type and meaning of the source is
	 * arbitrary, and is most useful as a secondary key for handler
	 * registration. (See {@link PlayerEventsBus#addHandlerToSource}, which allows a
	 * handler to register for events of a particular type, tied to a particular
	 * source.)
	 * <p>
	 * Note that the source is actually set at dispatch time, e.g. via
	 * {@link PlayerEventsBus#fireEventFromSource(Event, Object)}.
	 *
	 * @return object representing the source of this event
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * This is a method used primarily for debugging. It gives a string
	 * representation of the event details. This does not override the toString
	 * method because the compiler cannot always optimize toString out
	 * correctly. Event types should override as desired.
	 *
	 * @return a string representing the event's specifics.
	 */
	public String toDebugString() {
		String name = this.getClass().getName();
		name = name.substring(name.lastIndexOf(".") + 1);
		return "event: " + name + ":";
	}

	/**
	 * The toString() for abstract event is overridden to avoid accidently
	 * including class literals in the the compiled output. Use {@link Event}
	 * #toDebugString to get more information about the event.
	 */
	@Override
	public String toString() {
		return "An event type";
	}

	/**
	 * Implemented by subclasses to to invoke their handlers in a type safe
	 * manner. Intended to be called by {@link PlayerEventsBus#fireEvent(Event)} or
	 * {@link PlayerEventsBus#fireEventFromSource(Event, Object)}.
	 *
	 * @param handler
	 *            handler
	 * @see PlayerEventsBus#dispatchEvent(Event, Object)
	 */
	public abstract void dispatch(H handler);

	/**
	 * Set the source that triggered this event. Intended to be called by the
	 * {@link PlayerEventsBus} during dispatch.
	 *
	 * @param source
	 *            the source of this event.
	 * @see PlayerEventsBus#fireEventFromSource(Event, Object)
	 * @see PlayerEventsBus#setSourceOfEvent(Event, Object)
	 */
	protected void setSource(Object source) {
		this.source = source;
	}
}