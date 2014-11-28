package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface MediaExecutor<T extends Widget> {
	public MediaWrapper<T> getMediaWrapper();

	public void setMediaWrapper(MediaWrapper<T> descriptor);

	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration);

	public BaseMediaConfiguration getBaseMediaConfiguration();

	public void init();

	@Deprecated
	public void play(String src);

	public void play();

	public void stop();

	public void pause();

	public void setMuted(boolean mute);

	public void setVolume(double volume);

	public void setCurrentTime(double time);

	public void setSoundFinishedListener(SoundExecutorListener listener);
}
