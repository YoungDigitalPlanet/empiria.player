package eu.ydp.empiria.player.client.module.video.hack;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.*;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class ReAttachVideoPlayerForIOSChecker {

	public boolean isNeeded() {
		return isUserAgent(MobileUserAgent.SAFARI);
	}
}
