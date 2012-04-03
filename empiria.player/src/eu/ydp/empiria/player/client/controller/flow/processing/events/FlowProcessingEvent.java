package eu.ydp.empiria.player.client.controller.flow.processing.events;

public class FlowProcessingEvent {
	
	protected FlowProcessingEventType type;
	
	public FlowProcessingEvent(FlowProcessingEventType type){
		this.type = type;
	}
		
	public FlowProcessingEventType getType(){
		return type;
	}
}
