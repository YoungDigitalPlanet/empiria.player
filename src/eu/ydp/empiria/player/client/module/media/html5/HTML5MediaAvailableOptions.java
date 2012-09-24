package eu.ydp.empiria.player.client.module.media.html5;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class HTML5MediaAvailableOptions implements MediaAvailableOptions {

	@Override
	public boolean isPlaySupported() {
		return true;
	}

	@Override
	public boolean isPauseSupported() {
		return true;
	}

	@Override
	public boolean isMuteSupported() {
		return isVolumeChangeSupported();
	}

	@Override
	public boolean isVolumeChangeSupported() {
		//mobilna i inna niz ff i chrome bo tam dziala
		if (UserAgentChecker.getMobileUserAgent() != MobileUserAgent.UNKNOWN && !UserAgentChecker.isMobileUserAgent(MobileUserAgent.FIREFOX, MobileUserAgent.CHROME)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isStopSupported() {
		return true;
	}

	@Override
	public boolean isSeekSupported() {
		return true;
	}

	@Override
	public boolean isFullScreenSupported() {
		return true;
	}

	@Override
	public boolean isMediaMetaAvailable() {
		return true;
	}

	@Override
	public boolean isTemplateSupported() {
		return true;
	}

}
