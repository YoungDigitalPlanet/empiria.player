package eu.ydp.empiria.player.client.module.button;

import static eu.ydp.empiria.player.client.util.MapCreator.m;

import java.util.HashMap;
import java.util.Map;

import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;

public enum NavigationButtonDirection{
	NEXT, PREVIOUS;
	
	private static final Map<NavigationButtonDirection, String> DIR2NAME = m(new HashMap<NavigationButtonDirection, String>()).
							p(NEXT, "next").
							p(PREVIOUS, "prev");
	
	private static Map<NavigationButtonDirection, FlowRequest> DIR2REQUEST = m(new HashMap<NavigationButtonDirection, FlowRequest>()).
							p(NEXT, new FlowRequest.NavigateNextItem()).
							p(PREVIOUS, new FlowRequest.NavigatePreviousItem());
			
	public static final FlowRequest getRequest(NavigationButtonDirection direction){
		return DIR2REQUEST.get(direction);
	}
	
	public static final String getName(NavigationButtonDirection direction){
		return DIR2NAME.get(direction);
	}
}