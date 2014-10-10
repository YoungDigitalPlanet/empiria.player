package eu.ydp.empiria.player.client.module.simulation.soundjs;

public interface SoundApiForJs {
	void preload(final String src);

	void play(final String src);

	void playLooped(final String src);

	void stop(String src);
}
