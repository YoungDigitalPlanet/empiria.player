package eu.ydp.empiria.player.client.controller.events.internal;

import eu.ydp.empiria.player.client.module.IBrowserEventHandler;

public class InternalEventHandlerInfo extends InternalEventTrigger {

	public InternalEventHandlerInfo(IBrowserEventHandler m , String id, int e){
		super(id, e);
		module = m;
	}
	public InternalEventHandlerInfo(IBrowserEventHandler m , InternalEventTrigger t){
		super(t.getTagId(), t.getEventTypeInt());
		module = m;
	}
	
	protected IBrowserEventHandler module;

	/**
	 * @return the module
	 */
	public IBrowserEventHandler getModule() {
		return module;
	}
	
	
	public boolean match(String id, int e){
		return (eventTypeInt == e  &&  tagID.compareTo(id) == 0);
	}
	
}
