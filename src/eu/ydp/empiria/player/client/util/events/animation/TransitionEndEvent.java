package eu.ydp.empiria.player.client.util.events.animation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DomEvent;

import eu.ydp.empiria.player.client.event.animation.EventTypes;

public class TransitionEndEvent extends DomEvent<TransitionEndHandler> {

	private static final EventTypes EVENT_TYPES = GWT.create(EventTypes.class);

	private static final Type<TransitionEndHandler> TYPE = new Type<TransitionEndHandler>(EVENT_TYPES.getTransistionEnd(), new TransitionEndEvent());

	/**
	 * <p>getType</p>
	 *
	 * @return a Type object.
	 */
	public static Type<TransitionEndHandler> getType() {
		return TYPE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Gets the event type associated with transition end events.
	 */
	@Override
	public com.google.gwt.event.dom.client.DomEvent.Type<TransitionEndHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * <p>Constructor for TransitionEndEvent.</p>
	 */
	protected TransitionEndEvent() {

	}

	/** {@inheritDoc} */
	@Override
	protected void dispatch(TransitionEndHandler handler) {
		handler.onTransitionEnd(this);

	}


}