package eu.ydp.empiria.player.client.controller.events.interaction;

public interface MediaInteractionSoundEventCallback {

	public void onPlay();

	public void onStop();

	public void setCallforward(MediaInteractionSoundEventCallforward callforward);

}
