package eu.ydp.empiria.player.client.controller.messages;

public interface OperationMessageDisplayEventListener {

	public void onMessageHided(OperationMessage msg);
	public void onMessageAttaching(OperationMessage msg);
}
