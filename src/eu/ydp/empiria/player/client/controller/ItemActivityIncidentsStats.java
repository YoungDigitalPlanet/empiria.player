package eu.ydp.empiria.player.client.controller;

import java.util.HashMap;

import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;

public class ItemActivityIncidentsStats {

	public ItemActivityIncidentsStats(){
		incidentCounts = new HashMap<FlowActivityEventType, Integer>();
	}
	
	private HashMap<FlowActivityEventType, Integer> incidentCounts; 
	
	public void addFlowActivityIncident(FlowActivityEvent nit){
		if (!incidentCounts.containsKey(nit.getType()))
			incidentCounts.put(nit.getType(), 1);
		else
			incidentCounts.put(nit.getType(), incidentCounts.get(nit.getType()) + 1);
	}
	
	public int getFlowActivityIncidentsCount(FlowActivityEventType nit){
		if (!incidentCounts.containsKey(nit))
			return 0;
		else
			return incidentCounts.get(nit);
	}
}
