package eu.ydp.empiria.player.client.module;

import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class UserAgentCheckerWrapper {
	public boolean isStackAndroidBrowser() {
		return UserAgentChecker.isStackAndroidBrowser();
	}
}
