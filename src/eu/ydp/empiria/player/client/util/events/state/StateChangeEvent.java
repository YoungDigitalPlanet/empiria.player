package eu.ydp.empiria.player.client.util.events.state;

import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.util.events.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.EventTypes;

public class StateChangeEvent extends AbstractEvent<StateChangeEventHandler, StateChangeEventTypes> {
	public static EventTypes<StateChangeEventHandler, StateChangeEventTypes> types = new EventTypes<StateChangeEventHandler, StateChangeEventTypes>();
	private final StateChangedInteractionEvent value;

	public StateChangeEvent(StateChangeEventTypes type, StateChangedInteractionEvent value) {
		super(type, null);
		this.value = value;
	}

	public StateChangedInteractionEvent getValue() {
		return value;
	}

	@Override
	protected EventTypes<StateChangeEventHandler, StateChangeEventTypes> getTypes() {
		return types;
	}

	@Override
	public void dispatch(StateChangeEventHandler handler) {
		handler.onStateChange(this);
	}

	public static Type<StateChangeEventHandler, StateChangeEventTypes> getType(StateChangeEventTypes type) {
		return types.getType(type);
	}

}
