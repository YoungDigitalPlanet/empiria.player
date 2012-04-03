package eu.ydp.empiria.player.client.controller.flow.processing.events;

import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public class ActivityProcessingEvent extends FlowProcessingEvent {

	protected GroupIdentifier groupIdentifier;
	
	public ActivityProcessingEvent(FlowProcessingEventType type,GroupIdentifier gi) {
		super(type);	
		groupIdentifier = gi;
	}
	
	public GroupIdentifier getGroupIdentifier(){
		return groupIdentifier;
	}

}
