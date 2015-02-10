package eu.ydp.empiria.player.client.module.slideshow.sound;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.media.*;
import eu.ydp.empiria.player.client.module.slideshow.SlideEnd;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.*;
import java.util.Collection;

public class SlideSoundController implements MediaEventHandler {

	private final MediaWrapperController mediaWrapperController;
	private final SlideSounds slideSounds;
	private SlideEnd audioEnd;

	@Inject
	public SlideSoundController(EventsBus eventsBus, MediaWrapperController mediaWrapperController, SlideSounds slideTracks) {
		this.mediaWrapperController = mediaWrapperController;
		this.slideSounds = slideTracks;

		eventsBus.addHandler(MediaEvent.getType(MediaEventTypes.ON_END), this);
	}

	public void initSound(String audiopath) {
		slideSounds.initSound(audiopath);
	}

	public void playSound(String audiopath, SlideEnd audioEnd) {
		this.audioEnd = audioEnd;
		MediaWrapper<Widget> currentSound = slideSounds.getSound(audiopath);
		mediaWrapperController.play(currentSound);
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
			audioEnd.onEnd();
		}
	}
}
