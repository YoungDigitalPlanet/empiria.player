package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5AudioMediaExecutor;
import eu.ydp.empiria.player.client.media.Audio;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;

public class ReCreateAudioHack {
	
	@Inject
	private ReAttachAudioUtil reattachUtil;

	public void apply(AbstractHTML5MediaWrapper wrapper, HTML5AudioMediaExecutor executor) {
		Audio audio = (Audio) wrapper.getMediaObject();		
		Audio newAudio = reattachUtil.reAttachAudio(audio);
		wrapper.setMediaObject(newAudio);
		executor.setMedia(newAudio);
		executor.init();
	}
	
}
