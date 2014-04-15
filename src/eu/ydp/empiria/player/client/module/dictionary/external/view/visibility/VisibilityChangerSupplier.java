package eu.ydp.empiria.player.client.module.dictionary.external.view.visibility;

import com.google.common.base.Supplier;

import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class VisibilityChangerSupplier implements Supplier<VisibilityChanger> {

	@Override
	public VisibilityChanger get() {
		return createForCurrentUserAgent();
	}

	private VisibilityChanger createForCurrentUserAgent() {
		if (UserAgentChecker.isStackAndroidBrowser()) {
			return new AndroidVisibilityChanger();
		} else if (UserAgentChecker.isUserAgent(MobileUserAgent.SAFARI, MobileUserAgent.SAFARI_WEBVIEW)) {
			return new SafariMobileVisibilityChanger();
		} else {
			return new DefaultVisibilityChanger();
		}
	}

}
