package eu.ydp.empiria.player.client.controller.feedback.player;

import eu.ydp.empiria.player.client.module.media.html5.HTML5MediaAvailableOptions;

public class HTML5MediaForFeedbacksAvailableOptions extends HTML5MediaAvailableOptions {

	@Override
	public boolean isPauseSupported() {
		return false;
	}
}
