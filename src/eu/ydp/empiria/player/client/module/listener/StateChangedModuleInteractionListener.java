package eu.ydp.empiria.player.client.module.listener;

import eu.ydp.empiria.player.client.module.IInteractionModule;


public interface StateChangedModuleInteractionListener {

	public void onStateChanged(boolean userInteract, IInteractionModule sender);

}
