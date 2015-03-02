package eu.ydp.empiria.player.client.module.media.external;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;

public class ExternalFullscreenVideoMediaAvailableOptions implements MediaAvailableOptions {

	@Override
	public boolean isPlaySupported() {
		return true;
	}

	@Override
	public boolean isPauseSupported() {
		return false;
	}

	@Override
	public boolean isMuteSupported() {
		return false;
	}

	@Override
	public boolean isVolumeChangeSupported() {
		return false;
	}

	@Override
	public boolean isStopSupported() {
		return false;
	}

	@Override
	public boolean isSeekSupported() {
		return true;
	}

	@Override
	public boolean isFullScreenSupported() {
		return false;
	}

	@Override
	public boolean isMediaMetaAvailable() {
		return false;
	}

	@Override
	public boolean isTemplateSupported() {
		return true;
	}

}
