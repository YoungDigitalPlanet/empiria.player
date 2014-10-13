package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.executor;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorListener;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.ExternalMediaEngine;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class ExternalMediaExecutor implements MediaExecutor<Widget> {

	@Inject
	private ExternalMediaEngine mediaEngine;

	private MediaWrapper<Widget> wrapper;
	private BaseMediaConfiguration baseMediaConfiguration;

	@Override
	public MediaWrapper<Widget> getMediaWrapper() {
		return wrapper;
	}

	@Override
	public void setMediaWrapper(MediaWrapper<Widget> descriptor) {
		this.wrapper = descriptor;
	}

	@Override
	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {
		this.baseMediaConfiguration = baseMediaConfiguration;
	}

	@Override
	public BaseMediaConfiguration getBaseMediaConfiguration() {
		return baseMediaConfiguration;
	}

	@Override
	public void init() {
		mediaEngine.init(wrapper, baseMediaConfiguration.getSources().keySet());
	}

	@Override
	@Deprecated
	public void play(String src) {
		throw new UnsupportedOperationException("Operation is not supported. ExternalMediaExecutor.play(String src).");
	}

	@Override
	public void play() {
		mediaEngine.play(wrapper);
	}

	@Override
	public void playLooped() {
	}

	@Override
	public void stop() {
		mediaEngine.stop(wrapper);
	}

	@Override
	public void pause() {
		mediaEngine.pause(wrapper);
	}

	@Override
	public void resume() {
	}

	@Override
	public void setMuted(boolean mute) {
	}

	@Override
	public void setVolume(double volume) {
	}

	@Override
	public void setCurrentTime(double time) {
		mediaEngine.setCurrentTime(wrapper, time);
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
	}
}
