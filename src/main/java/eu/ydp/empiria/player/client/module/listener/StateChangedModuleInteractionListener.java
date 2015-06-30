package eu.ydp.empiria.player.client.module.listener;

import eu.ydp.empiria.player.client.module.IUniqueModule;

public interface StateChangedModuleInteractionListener {

    public void onStateChanged(boolean userInteract, IUniqueModule sender);

}
