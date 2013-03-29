package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector;

public interface MediaConnector {

	void init(String id, Iterable<String> sources);
	void seek(String id, int timeMillis);
	void play(String id);
	void pause(String id);
}
