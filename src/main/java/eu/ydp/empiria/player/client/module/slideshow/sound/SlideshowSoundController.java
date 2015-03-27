package eu.ydp.empiria.player.client.module.slideshow.sound;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.media.*;
import eu.ydp.empiria.player.client.module.slideshow.SlideEndHandler;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.*;
import java.util.Collection;

public class SlideshowSoundController implements MediaEventHandler {

	private final MediaWrapperController mediaWrapperController;
	private final SlideshowSounds slideshowSounds;
	private SlideEndHandler slideEndHandler;

	@Inject
	public SlideshowSoundController(EventsBus eventsBus, MediaWrapperController mediaWrapperController, SlideshowSounds slideshowSounds) {
		this.mediaWrapperController = mediaWrapperController;
		this.slideshowSounds = slideshowSounds;

		eventsBus.addHandler(MediaEvent.getType(MediaEventTypes.ON_END), this);
	}

	public void initSound(String audiopath) {
		slideshowSounds.initSound(audiopath);
	}

	public void playSound(String audiopath, SlideEndHandler slideEndhandler) {
		this.slideEndHandler = slideEndhandler;
		MediaWrapper<Widget> currentSound = slideshowSounds.getSound(audiopath);
		mediaWrapperController.play(currentSound);
	}

	public void pauseSound(String audiopath) {
		MediaWrapper<Widget> currentSound = slideshowSounds.getSound(audiopath);
		mediaWrapperController.pause(currentSound);
	}

	public void stopAllSounds() {
		Collection<MediaWrapper<Widget>> sounds = slideshowSounds.getAllSounds();
		for (MediaWrapper<Widget> sound : sounds) {
			mediaWrapperController.stop(sound);
		}
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		MediaWrapper<Widget> onEndEventSource = (MediaWrapper<Widget>) event.getMediaWrapper();

		if (slideshowSounds.containsWrapper(onEndEventSource)) {
			slideEndHandler.onEnd();
		}
	}
}
