package eu.ydp.empiria.player.client.animation;

import javax.annotation.Nonnull;

import com.google.inject.Inject;

import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class CssAnimationSupportAnalizer {
	@Inject @Nonnull UserAgentUtil userAgentUtil;

	public boolean isCssAnimationSupported(){
		if(userAgentUtil.isMobileUserAgent()){
			return hasSupportCurrentMobileBrowser();
		}
		return hasSupportCurrentDesktopBrowser();
	}

	private boolean hasSupportCurrentMobileBrowser() {
		return userAgentUtil.isMobileUserAgent(
									MobileUserAgent.CHROME,
									MobileUserAgent.SAFARI,
									MobileUserAgent.FIREFOX,
									MobileUserAgent.ANDROID4);
	}

	private boolean hasSupportCurrentDesktopBrowser() {
		return userAgentUtil.isUserAgent(
									UserAgent.GECKO1_8,
									UserAgent.SAFARI);
	}
}
