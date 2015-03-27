package eu.ydp.empiria.player.client.module.media.progress;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface MediaProgressBar {
	int getScrollWidth();

	MediaWrapper<?> getMediaWrapper();

	void moveScroll(int position);

	int getButtonWidth();
}