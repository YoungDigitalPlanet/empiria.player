package eu.ydp.empiria.player.client.module.slideshow.sound;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.media.*;
import eu.ydp.empiria.player.client.module.slideshow.structure.AudioBean;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.*;
import java.util.*;

public class SlideSoundController implements MediaEventHandler {

	private final MediaWrapperController mediaWrapperController;
	private final SlideSounds slideSounds;
	private Command audioEnd;

	@Inject
	public SlideSoundController(EventsBus eventsBus, MediaWrapperController mediaWrapperController, SlideSounds slideTracks) {
		this.mediaWrapperController = mediaWrapperController;
		this.slideSounds = slideTracks;

		eventsBus.addHandler(MediaEvent.getType(MediaEventTypes.ON_END), this);
	}

	public void initSounds(List<AudioBean> sounds) {
		for (AudioBean sound : sounds) {
			slideSounds.initSound(sound.getSrc());
		}
	}

	public void playSound(String audiopath, Command audioEnd) {
		this.audioEnd = audioEnd;
		MediaWrapper<Widget> currentSound = slideSounds.getSound(audiopath);
		mediaWrapperController.play(currentSound);
	}

	public void stopSound(String audiopath) {
		MediaWrapper<Widget> currentSound = slideSounds.getSound(audiopath);
		mediaWrapperController.stop(currentSound);
	}

	public void pauseSound(String audiopath) {
		MediaWrapper<Widget> currentSound = slideSounds.getSound(audiopath);
		mediaWrapperController.pause(currentSound);
	}

	public void stopAllSounds() {
		Collection<MediaWrapper<Widget>> sounds = slideSounds.getAllSounds();
		for (MediaWrapper<Widget> sound : sounds) {
			mediaWrapperController.stop(sound);
		}
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		
		MediaWrapper<Widget> onEndEventSource = (MediaWrapper<Widget>) event.getMediaWrapper();

		if (slideSounds.containsWrapper(onEndEventSource)) {
			audioEnd.execute();
		}
	}
}
