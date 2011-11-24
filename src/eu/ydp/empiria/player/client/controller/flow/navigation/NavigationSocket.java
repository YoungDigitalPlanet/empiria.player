package eu.ydp.empiria.player.client.controller.flow.navigation;

import eu.ydp.empiria.player.client.controller.communication.ItemParametersSocket;

public interface NavigationSocket  {

	public NavigationViewSocket getNavigationViewSocket();
	public void setItemParamtersSocket(ItemParametersSocket ips);
}
