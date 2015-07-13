package eu.ydp.empiria.player.client.module.dictionary.external.view.visibility;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class VisibilityChangerProvider implements Provider<VisibilityChanger> {

    @Inject
    private UserAgentUtil userAgentUtil;
    @Inject
    private Provider<AndroidVisibilityChanger> androidVisibilityChangerProvider;
    @Inject
    private Provider<SafariMobileVisibilityChanger> safariMobileVisibilityChangerProvider;
    @Inject
    private Provider<DefaultVisibilityChanger> defaultVisibilityChangerProvider;

    @Override
    public VisibilityChanger get() {
        if (userAgentUtil.isStackAndroidBrowser()) {
            return androidVisibilityChangerProvider.get();
        } else if (userAgentUtil.isUserAgent(MobileUserAgent.SAFARI, MobileUserAgent.SAFARI_WEBVIEW)) {
            return safariMobileVisibilityChangerProvider.get();
        } else {
            return defaultVisibilityChangerProvider.get();
        }
    }
}
