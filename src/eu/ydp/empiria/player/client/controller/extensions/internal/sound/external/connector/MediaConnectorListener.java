package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.MediaParams;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.MediaStatus;

public interface MediaConnectorListener {

	void onReady(String id, MediaParams params);
	void onPlay(String id);
	void onPause(String id);
	void onEnd(String id);
	void onTimeUpdate(String id, MediaStatus status);
}
