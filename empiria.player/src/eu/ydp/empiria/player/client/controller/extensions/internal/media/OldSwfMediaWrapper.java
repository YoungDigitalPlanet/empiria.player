package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

/**
 * Stary player swf
 * @author plelakowski
 *
 */
public class OldSwfMediaWrapper implements MediaWrapper<Widget> {
	Widget mediaWidget = null;
	@Override
	public MediaAvailableOptions getMediaAvailableOptions() {
		return new OldSwfMediaAvaliableOptions();
	}

	@Override
	public Widget getMediaObject() {
		return mediaWidget;
	}

	public void setMediaWidget(Widget mediaWidget) {
		this.mediaWidget = mediaWidget;
	}

	@Override
	public String getMediaUniqId() {
		return null;
	}

	@Override
	public double getCurrentTime() {
		return 0;
	}

	@Override
	public double getDuration() {
		return 0;
	}

	@Override
	public boolean isMuted() {
		return false;
	}

	@Override
	public double getVolume() {
		return 0;
	}

}
