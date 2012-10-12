package eu.ydp.empiria.player.client.util.events.animation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DomEvent;

import eu.ydp.empiria.player.client.event.animation.EventTypes;

public class AnimationEndEvent extends DomEvent<AnimationEndHandler> {

	private static final EventTypes EVENT_TYPES = GWT.create(EventTypes.class);

	private static final Type<AnimationEndHandler> TYPE = new Type<AnimationEndHandler>(EVENT_TYPES.getAnimationEnd(), new AnimationEndEvent());

	/**
	 * <p>getType</p>
	 *
	 * @return a Type object.
	 */
	public static Type<AnimationEndHandler> getType() {
		return TYPE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Gets the event type associated with transition end events.
	 */
	@Override
	public com.google.gwt.event.dom.client.DomEvent.Type<AnimationEndHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * <p>Constructor for TransitionEndEvent.</p>
	 */
	protected AnimationEndEvent() {

	}

	/** {@inheritDoc} */
	@Override
	protected void dispatch(AnimationEndHandler handler) {
		handler.onAnimationEnd(this);

	}


}