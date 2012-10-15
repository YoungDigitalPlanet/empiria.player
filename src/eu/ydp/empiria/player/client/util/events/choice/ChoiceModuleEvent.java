package eu.ydp.empiria.player.client.util.events.choice;

import eu.ydp.empiria.player.client.util.events.AbstractEvent;
import eu.ydp.empiria.player.client.util.events.EventTypes;

public class ChoiceModuleEvent extends AbstractEvent<ChoiceModuleEventHandler, ChoiceModuleEventType> {
	
	private static EventTypes<ChoiceModuleEventHandler, ChoiceModuleEventType> types = new EventTypes<ChoiceModuleEventHandler, ChoiceModuleEventType>();
	
	private String choiceIdentifier;
	
	public ChoiceModuleEvent(ChoiceModuleEventType type, String choiceIdentifier){
		this(type, (Object)null);
		this.choiceIdentifier = choiceIdentifier;
	}
	
	public ChoiceModuleEvent(ChoiceModuleEventType type, Object source) {
		super(type, source);
	}
	
	public static Type<ChoiceModuleEventHandler, ChoiceModuleEventType> getType(ChoiceModuleEventType type){
		return types.getType(type);
	}

	@Override
	protected EventTypes<ChoiceModuleEventHandler, ChoiceModuleEventType> getTypes() {
		return types;
	}

	@Override
	public void dispatch(ChoiceModuleEventHandler handler) {
		handler.onChoiceModuleEvent(this);
	}

	public String getChoiceIdentifier() {
		return choiceIdentifier;
	}

}
