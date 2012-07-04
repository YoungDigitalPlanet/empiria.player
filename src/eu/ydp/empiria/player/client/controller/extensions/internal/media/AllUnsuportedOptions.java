package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;

public class AllUnsuportedOptions implements MediaAvailableOptions {

	@Override
	public boolean isPlaySupported() {
		return false;
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
		return false;
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
		return false;
	}

}
