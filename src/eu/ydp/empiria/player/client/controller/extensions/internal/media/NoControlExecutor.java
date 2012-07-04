package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public abstract class NoControlExecutor implements SoundExecutor<Widget> {

	protected MediaWrapper<Widget> mediaWrapper;
	protected BaseMediaConfiguration bmc;

	@Override
	public MediaWrapper<Widget> getMediaWrapper() {
		return mediaWrapper;
	}

	@Override
	public void setMediaWrapper(MediaWrapper<Widget> descriptor) {
		this.mediaWrapper = descriptor;

	}

	@Override
	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {
		this.bmc = baseMediaConfiguration;

	}


	@Override
	@Deprecated
	public void play(String src) {
		// TODO Auto-generated method stub

	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMuted(boolean mute) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVolume(double volume) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCurrentTime(double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
		// TODO Auto-generated method stub

	}

}
