package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public abstract class AbstractNoControlExecutor implements MediaExecutor<Widget> {

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
	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {// NOPMD
		this.bmc = baseMediaConfiguration;
	}

	@Override
	public BaseMediaConfiguration getBaseMediaConfiguration() {
		return bmc;
	}

	@Override
	@Deprecated
	public void play(String src) {// NOPMD
	}

	@Override
	public void play() {// NOPMD
	}

	@Override
	public void stop() {// NOPMD
	}

	@Override
	public void pause() {// NOPMD
	}

	@Override
	public void playLooped() {
	}

	@Override
	public void setMuted(boolean mute) {// NOPMD

	}

	@Override
	public void setVolume(double volume) {// NOPMD

	}

	@Override
	public void setCurrentTime(double time) {// NOPMD

	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {// NOPMD

	}

}
