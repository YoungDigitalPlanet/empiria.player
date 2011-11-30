package eu.ydp.empiria.player.client.module;

import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;

public interface MediaModuleInteractionListener {

	public void onMediaSoundPlay(String url, MediaInteractionSoundEventCallback callback);
}
