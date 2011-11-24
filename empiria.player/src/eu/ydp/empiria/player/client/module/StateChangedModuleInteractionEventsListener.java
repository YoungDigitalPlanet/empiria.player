package eu.ydp.empiria.player.client.module;

public interface StateChangedModuleInteractionEventsListener {

	public void onStateChanged(boolean userInteract, IInteractionModule sender);

}
