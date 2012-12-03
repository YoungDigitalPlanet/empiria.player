package eu.ydp.empiria.player.client.module.media.html5;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent.IE8;
import static eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent.IE9;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.util.HTML5FullScreenHelper;
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
		return HTML5FullScreenHelper.isFullScreenSupported() || UserAgentChecker.isMobileUserAgent() || UserAgentChecker.isUserAgent(IE8, IE9);
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
