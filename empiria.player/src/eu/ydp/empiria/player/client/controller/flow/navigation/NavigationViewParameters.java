package eu.ydp.empiria.player.client.controller.flow.navigation;

import eu.ydp.empiria.player.client.controller.communication.PageType;

public class NavigationViewParameters {

	public NavigationViewParameters(PageType pt, int currentItemIndex, boolean isFirstItem, boolean isLastItem){
		pageType = pt;
		this.currentItemIndex = currentItemIndex;
		this.isFirstItem = isFirstItem;
		this.isLastItem = isLastItem;
	}
	
	public PageType pageType;
	public int currentItemIndex;
	public boolean isFirstItem;
	public boolean isLastItem;
	
}
