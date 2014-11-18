package eu.ydp.empiria.player.client.module.media.progress;

import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.*;

import java.util.*;

import eu.ydp.empiria.player.client.util.events.media.*;

public class ProgressBarUpdateEventHandler extends AbstractMediaEventHandler {
	Set<MediaEventTypes> fastUpdateEvents = new HashSet<MediaEventTypes>(Arrays.asList(new MediaEventTypes[] { ON_FULL_SCREEN_SHOW_CONTROLS, ON_STOP,
			MediaEventTypes.ON_DURATION_CHANGE }));
	// -1 aby przy pierwszym zdarzeniu pokazal sie timer
	private int lastTime = -1;
	private final MediaProgressBarImpl progressBar;

	public ProgressBarUpdateEventHandler(MediaProgressBarImpl progressBar) {
		this.progressBar = progressBar;
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		if (progressBar.isMediaReady() && !progressBar.isPressed()) {
			double currentTime = progressBar.getMediaWrapper().getCurrentTime();

			if (currentTime > lastTime + 1 || currentTime < lastTime || fastUpdateEvents.contains(event.getType())) {
				// przeskakujemy co sekunde
				lastTime = (int) progressBar.getMediaWrapper().getCurrentTime();
				double steep = progressBar.getScrollWidth() / progressBar.getMediaWrapper().getDuration();
				progressBar.moveScroll((int) (steep * lastTime));
			}
		}
	}

	public void resetCurrentTime() {
		lastTime = -1;
	}
}
