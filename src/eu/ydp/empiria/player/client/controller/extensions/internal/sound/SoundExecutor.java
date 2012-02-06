package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

public interface SoundExecutor {

	public void play(String src);
	public void stop();
	public void setSoundFinishedListener(SoundExecutorListener listener);
}
