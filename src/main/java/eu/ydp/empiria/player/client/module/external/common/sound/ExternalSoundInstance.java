package eu.ydp.empiria.player.client.module.external.common.sound;

import com.google.gwt.core.client.js.JsType;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;

@JsType
public class ExternalSoundInstance {

	private final MediaWrapperController mediaWrapperController;
	private final MediaWrapper<Widget> audioWrapper;

	@Inject
	public ExternalSoundInstance(@Assisted MediaWrapper<Widget> audioWrapper, MediaWrapperController mediaWrapperController) {
		this.audioWrapper = audioWrapper;
		this.mediaWrapperController = mediaWrapperController;
	}

	public void play() {
		mediaWrapperController.stopAndPlay(audioWrapper);
	}

	public void playLooped() {
		mediaWrapperController.playLooped(audioWrapper);
	}

	public void stop() {
		mediaWrapperController.stop(audioWrapper);
	}

	public void pause() {
		mediaWrapperController.pause(audioWrapper);
	}

	public void resume() {
		mediaWrapperController.resume(audioWrapper);
	}
}
