package eu.ydp.empiria.player.client.controller.body;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.HasWidgets;

public class PlayerContainersAccessor  {

	private Map<Integer, HasWidgets> itemContainers = new HashMap<Integer, HasWidgets>();
	private HasWidgets playerContainer;
	
	public void registerItemBodyContainer(int itemIndex, HasWidgets container){
		this.itemContainers.put(itemIndex, container);
	}
	
	public HasWidgets getItemBodyContainer(int itemIndex){
		return itemContainers.get(itemIndex);
	}
	
	public void setPlayerContainer(HasWidgets playerContainer){
		this.playerContainer = playerContainer;
	}
		
	public HasWidgets getPlayerContainer(){
		return playerContainer;
	}

}
