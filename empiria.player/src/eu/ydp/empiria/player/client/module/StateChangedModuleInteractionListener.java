package eu.ydp.empiria.player.client.module;

public interface StateChangedModuleInteractionListener {

	public void onStateChanged(boolean userInteract, IInteractionModule sender);

}
