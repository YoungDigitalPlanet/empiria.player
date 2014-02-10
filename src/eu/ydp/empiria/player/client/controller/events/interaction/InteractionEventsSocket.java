package eu.ydp.empiria.player.client.controller.events.interaction;

public interface InteractionEventsSocket extends InteractionEventsListener {

	void addStateChangedInteractionEventsListener(StateChangedInteractionEventListener stateChangedListener);

	void removeStateChangedInteractionEventsListener(StateChangedInteractionEventListener stateChangedListener);
}
